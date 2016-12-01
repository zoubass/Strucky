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

	/**
	 * DATABASE LOOK UPS FOR ENTITY
	 */
	public Application getApplication(SystemApp system) {
		if (cache.contains(system)) {
			return cache.get(system);
		} else {
			Application app = applicationRepo.findBySystemId(system.getId());
			if (app != null) {
				cache.cacheApplication(app);
				return app;
			} else {
				return createApplication(system);
			}
		}
	}

	public Method getConsumedMethod(Application provider, Message msg) {
		if (cache.contains(provider,msg.getMsg_type(),msg.getMsg_version())) {
			return cache.get(provider,msg.getMsg_type(),msg.getMsg_version());
		} else {
			Method method = methodRepository.findProvidedMethod(provider, msg.getMsg_type(), msg.getMsg_version());
			if (method != null) {
				cache.cacheMethod(provider, method);
				return method;
			} else {
				return createMethod(provider, msg);
			}
		}
	}

	public void createConsumeRelation(Application consumer, Method method) {
		ConsumeRelationship relationship = null;

		if (cache.contains(consumer, method)) {
			relationship = cache.get(consumer,method);
		} else {
			relationship = relationshipRepo.findRelationship(consumer, method);
		}

		if (relationship != null) {
			cache.cacheRelation(relationship);
			log.debug(String.format("Relationship: %s CONSUMES -> %s, already exists, incrementing total usage by 1.",
					consumer.getName(), method.getName()));
			relationship.setTotalUsage(relationship.getTotalUsage() + 1);
		} else {
			createRelationship(consumer, method);
		}
	}

	/**
	 * PERSISTING CACHED RELATIONS INTO GRAPH
	 */
	public void persistCachedRelations() {
		log.info("Saving cached relations into the database.");
		relationshipRepo.save(cache.getRelations());
		log.info("Saved.");
	}

	/**
	 * DYNAMIC CREATION METHODS
	 */
	private Application createApplication(SystemApp system) {
		log.info(String.format("Creating application: %s with systemID: %s",system.getName(),system.getId()));
		Application app = new Application(system.getName(), system.getId(), new ArrayList<Method>());
		cache.cacheApplication(app);
		return applicationRepo.save(app);
	}

	private Method createMethod(Application provider, Message msg) {
		Method method = new Method(msg.getMsg_type(), msg.getMsg_version());
		log.info(String.format("Creating method: %s, version: %s for app %s.", msg.getMsg_type(), msg.getMsg_version(),
				provider.getName()));

		if (provider.getProvidedMethods() != null) {
			provider.getProvidedMethods().add(method);
		} else {
			provider.setProvidedMethods(Lists.newArrayList(method));
		}
		applicationRepo.save(provider);
		cache.cacheMethod(provider, method);
		log.info(String.format("Application: %s successfully saved.", provider.getName()));
		return method;
	}

	private void createRelationship(Application consumer, Method method) {
		ConsumeRelationship consumeRelationship = new ConsumeRelationship(consumer, method, 1L);
		log.info(String.format("Creating new relationship: %s CONSUMES -> %s.", consumer.getName(), method.getName()));
		if (consumer.getConsumeRelationship() != null) {
			consumer.getConsumeRelationship().add(consumeRelationship);
		} else {
			consumer.setConsumeRelationship(Lists.newArrayList(consumeRelationship));
		}
		applicationRepo.save(consumer);
		cache.cacheRelation(consumeRelationship);
	}
}
