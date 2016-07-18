package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.*;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.service.DataConversionService;
import cz.zoubelu.service.GraphService;
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
public class DataConversionServiceImpl implements DataConversionService {
	private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private InformaDao informaDao;

    @Autowired
    private GraphService graphService;


    @Override
	public void convertData(TimeRange timeRange) {
		log.info("Starting conversion.");
		List<Message> messages = informaDao.getInteractionData(timeRange);
		for (Message msg : messages) {
			Application providingApp = graphService.findByName(msg.getApplication());
			Method consumedMethod = getConsumedMethod(providingApp, msg);
			Application consumingApp = getConsumerApplication(msg);

			log.info(String.format("Prepared relationship - Consumer: %s, Method: %s.",consumingApp.getName(), consumedMethod.getName()));

			createConsumeRelation(consumingApp, consumedMethod);
			//TODO: vratit result
		}
	}

	private Method getConsumedMethod(Application app, Message msg) {
		if (app != null) {
			Method method = graphService.findProvidedMethodOfApplication(app, msg.getMsg_type(), msg.getMsg_version());
			return getOrCreateMethod(app, method, msg);
		} else {
			log.debug(String.format("Application %s not found in graph database, creating new one.",msg.getApplication()));
			app = new Application(msg.getApplication(), new ArrayList<Method>());
			return getOrCreateMethod(app, null, msg);
		}
	}

	private Method getOrCreateMethod(Application app, Method method, Message msg) {
		if (method != null) {
			return method;
		} else {
			log.debug(String.format("Creating method: %s, version: %s for app %s.",msg.getMsg_type(),msg.getMsg_version(),app.getName()));
			method = new Method(msg.getMsg_type(), msg.getMsg_version());

			if (app.getProvidedMethods() != null) {
				app.getProvidedMethods().add(method);
			} else {
				app.setProvidedMethods(Lists.newArrayList(method));
			}
			graphService.saveApp(app);
			log.debug(String.format("Application: %s successfully saved.",app.getName()));
			return method;
		}
	}

    private Application getConsumerApplication(Message msg) {
        Application consumer;
        String systemName = SystemID.getSystemByID(msg.getMsg_src_sys());
        if (systemName != null) {
            consumer = graphService.findByName(systemName);
            return (consumer != null) ? consumer : createConsumerApp(systemName);
		} else {
            consumer = createConsumerApp(msg.getMsg_src_sys().toString());
        }
        return consumer;
    }


	private Application createConsumerApp(String name) {
		Application consumer = new Application(name, new ArrayList<Method>());
		graphService.saveApp(consumer);
		return consumer;
	}

	private void createConsumeRelation(Application consumer, Method method) {
		ConsumeRelationship consumeRelationship = graphService.findRelationship(consumer, method);
		if (consumeRelationship != null) {
			log.debug(String.format("Relationship: %s CONSUMES -> %s, already exists, incrementing total usage by 1.",consumer.getName(),method.getName()));
			consumeRelationship.setTotalUsage(consumeRelationship.getTotalUsage() + 1);
		} else {
			log.debug(String.format("Creating new relationship: %s CONSUMES -> %s.",consumer.getName(),method.getName()));
			consumeRelationship = new ConsumeRelationship(consumer, method, 1L);
			consumer.setConsumeRelationship(Lists.newArrayList(consumeRelationship));
			graphService.saveApp(consumer);
		}
		graphService.saveRel(consumeRelationship);
	}

}
