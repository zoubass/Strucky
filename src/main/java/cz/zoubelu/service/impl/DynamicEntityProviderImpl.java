package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.cache.Cache;
import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.MethodRepository;
import cz.zoubelu.repository.RelationshipRepository;
import cz.zoubelu.service.DynamicEntityProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by t922274 on 8.11.2016.
 */
@Component
public class DynamicEntityProviderImpl implements DynamicEntityProvider {
	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	private ApplicationRepository applicationRepo;

	@Autowired
	private Cache cache;

	@Autowired
	private RelationshipRepository relationshipRepo;

	@Autowired
	private MethodRepository methodRepository;

	public Application getApplication(SystemApp system) {
		if (cache.contains(system)) {
			return cache.get(system);
		} else {
			Application appById = applicationRepo.findBySystemId(system.getId());
			return appById != null ? appById:createApplication(system);
		}
	}

	public Method getMethod(Application application, Message msg) {
		Method method = methodRepository.findProvidedMethod(application, msg.getMsg_type(), msg.getMsg_version());
		if (method != null){
			return method;
		}else{
			return createMethod(application, msg);
		}
	}

	public ConsumeRelationship getRelationship(Application application, Method method) {
		//check if its in cache, otherwise check in database
		if (cache.contains(application, method)) {
			return cache.get(application,method);
		} else {
			return relationshipRepo.findRelationship(application, method);
		}
	}

	private Application createApplication(SystemApp system) {
		log.info(String.format("Creating application: %s with systemID: %s",system.getName(),system.getId()));
		Application app = new Application(system.getName(), system.getId(), new ArrayList<Method>());
		return applicationRepo.save(app);
	}

	private Method createMethod(Application app, Message msg) {
		Method method = new Method(msg.getMsg_type(), msg.getMsg_version());
		log.info(String.format("Creating method: %s, version: %s for app %s.", msg.getMsg_type(), msg.getMsg_version(),
				app.getName()));

		if (app.getProvidedMethods() != null) {
			app.getProvidedMethods().add(method);
		} else {
			app.setProvidedMethods(Lists.newArrayList(method));
		}
		applicationRepo.save(app);
		log.info(String.format("Application: %s successfully saved.", app.getName()));
		return method;
	}
}
