package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.*;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.repository.MethodRepository;
import cz.zoubelu.repository.RelationshipRepository;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.ConversionResult;
import cz.zoubelu.utils.SystemID;
import cz.zoubelu.utils.TimeRange;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoubas on 10.7.16.
 */
@Service
@Transactional
public class DataConversionImpl implements DataConversion {
	private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private InformaDao informaDao;

	@Autowired
	private ApplicationRepository applicationRepo;

	@Autowired
	private MethodRepository methodRepository;

	@Autowired
	private RelationshipRepository relationshipRepo;



	@Override
	public ConversionResult convertData(TimeRange timeRange) {
		List<Message> messages = informaDao.getInteractionData(timeRange);
		return convertData(messages);
	}

	@Override
	public ConversionResult convertData(List<Message> messages) {
		log.info("Starting conversion.");
		ConversionResult result = new ConversionResult();
		for (Message msg : messages) {
//			validate(msg);
			Application providingApp = getProvidingApp(msg);
			Method consumedMethod = getConsumedMethod(providingApp, msg);
			Application consumingApp = getConsumerApplication(msg);

			log.info(String.format("Prepared relationship - Provider: %s, Method: %s, Consumer: %s.",
					providingApp.getName(), consumedMethod.getName(), consumingApp.getName()));

			createConsumeRelation(consumingApp, consumedMethod);
		}
		return result;
	}

	private Application getProvidingApp(Message msg) {
		Application app = applicationRepo.findByName(msg.getApplication());
		return createApplicationIfNull(app, msg.getApplication());
	}

	private Method getConsumedMethod(Application app, Message msg) {
		Method method = methodRepository.findProvidedMethodOfApplication(app, msg.getMsg_type(), msg.getMsg_version());
		return createMethodIfNull(app, method, msg);
	}


	private Application getConsumerApplication(Message msg) {
		String systemName = SystemID.getSystemByID(msg.getMsg_src_sys());
		if (systemName != null) {
			return createApplicationIfNull(applicationRepo.findByName(systemName),systemName);
		} else {
			// neni vedena v systemech ale chci o ni vedet v grafu, prozatim urcite
			return createApplicationIfNull(null,msg.getMsg_src_sys().toString());
		}
	}

	private void createConsumeRelation(Application consumer, Method method) {
		ConsumeRelationship consumeRelationship = relationshipRepo.findRelationship(consumer, method);
		if (consumeRelationship != null) {
			log.debug(String.format("Relationship: %s CONSUMES -> %s, already exists, incrementing total usage by 1.",consumer.getName(),method.getName()));
			consumeRelationship.setTotalUsage(consumeRelationship.getTotalUsage() + 1);
		} else {
			consumeRelationship = createRelationship(consumer, method);
		}
		relationshipRepo.save(consumeRelationship);
	}



	// HELPER METHODS, SHOULD BE IN UTILS
	private Application createApplicationIfNull(Application app, String appName) {
		if (app != null) {
			return app;
		} else {
			app = new Application(appName, new ArrayList<Method>());
			return applicationRepo.save(app);
		}
	}

	private Method createMethodIfNull(Application app, Method method, Message msg) {
		if (method != null) {
			return method;
		} else {
			return createMethod(app, msg);
		}
	}

	// NEW METHOD CREATION
	private Method createMethod(Application app, Message msg) {
		Method method = new Method(msg.getMsg_type(), msg.getMsg_version());
		log.debug(String.format("Creating method: %s, version: %s for app %s.", msg.getMsg_type(), msg.getMsg_version(),
				app.getName()));

		if (app.getProvidedMethods() != null) {
			app.getProvidedMethods().add(method);
		} else {
			app.setProvidedMethods(Lists.newArrayList(method));
		}
		applicationRepo.save(app);
		log.debug(String.format("Application: %s successfully saved.", app.getName()));
		return method;
	}
	// NEW RELATIONSHIP IN CASE IT DOESNT EXIST YET
	private ConsumeRelationship createRelationship (Application consumer, Method method) {
		ConsumeRelationship consumeRelationship = new ConsumeRelationship(consumer, method, 1L);
		log.debug(String.format("Creating new relationship: %s CONSUMES -> %s.",consumer.getName(),method.getName()));
		if (consumer.getConsumeRelationship() != null){
			consumer.getConsumeRelationship().add(consumeRelationship);
		}else{
			consumer.setConsumeRelationship(Lists.newArrayList(consumeRelationship));
		}
		applicationRepo.save(consumer);
		return consumeRelationship;
	}
}
