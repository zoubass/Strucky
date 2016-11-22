package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.cache.Cache;
import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.codelist.SystemsList;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.InformaMessageRepository;
import cz.zoubelu.repository.RelationshipRepository;
import cz.zoubelu.repository.mapper.MessageMapper;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.service.DynamicEntityProvider;
import cz.zoubelu.utils.ConversionError;
import cz.zoubelu.utils.CsvFileUtils;
import cz.zoubelu.utils.TimeRange;
import cz.zoubelu.validation.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Transactional;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zoubas on 10.7.16.
 */
@Transactional
public class DataConversionImpl implements DataConversion, RowCallbackHandler{
	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	private Cache cache;

	@Autowired
	private ApplicationRepository applicationRepo;

	@Autowired
	private RelationshipRepository relationshipRepo;

	@Autowired
	private Validator validator;

	@Autowired
	private DynamicEntityProvider provider;

	@Autowired
	private InformaMessageRepository informaRepository;

	private SystemsList systemsList;

	final Set<ConversionError> errors;



	public DataConversionImpl(){
		errors= new HashSet<ConversionError>();
	}

	public List<ConversionError> convertData(String tableName, TimeRange timeRange) {
		informaRepository.fetchAndConvertData(tableName, timeRange, this);
		return persistCacheAndFinish();
	}

	public List<ConversionError> convertData(String tableName) {
		informaRepository.fetchAndConvertData(tableName,this);
		return persistCacheAndFinish();
	}
	//TODO: smazat
	public List<ConversionError> convertData(List<Message> messages) {
		final Set<ConversionError> errors = new HashSet<ConversionError>();

		for (Message message : messages) {
			try {
				convertSingleMessage(message);
			} catch (Exception e) {
				log.error("Conversion of application failed. Reason: " + e.getMessage(), e);
				errors.add(new ConversionError(e.getMessage()));
			}
		}
		return Lists.newArrayList(errors);
	}

	/**
	 * The main method for mapping the message onto graph.
	 * @param msg
	 */
	public void convertSingleMessage(Message msg) {
		validator.validateMessage(msg);
		Application providingApp = getProvidingApplication(msg);
		Method consumedMethod = getConsumedMethod(providingApp, msg);
		Application consumingApp = getConsumingApplication(msg);

		log.info(String.format("Saving relationship - Provider: %s, Method: %s, Consumer: %s.", providingApp.getName(),
				consumedMethod.getName(), consumingApp.getName()));

		createConsumeRelation(consumingApp, consumedMethod);
	}

	private Application getProvidingApplication(Message msg) {
		SystemApp system = systemsList.getIdByName(msg.getApplication());
		boolean isNewlyCreated = system.getId().equals(-1);
		if (isNewlyCreated) {
			if (msg.getMsg_tar_sys() != null) {
				system.setId(msg.getMsg_tar_sys());
			} else {
				log.error("Unable to get system ID for application with name: " + system.getName());
			}
		}
		return provider.getApplication(system);
	}

	private Application getConsumingApplication(Message msg) {
		SystemApp system = systemsList.getSystemByID(msg.getMsg_src_sys());
		return provider.getApplication(system);
	}

	private Method getConsumedMethod(Application app, Message msg) {
		return provider.getMethod(app,msg);
	}

	private void createConsumeRelation(Application consumer, Method method) {
		ConsumeRelationship consumeRelationship = provider.getRelationship(consumer,method);

		if (consumeRelationship != null) {
			log.debug(String.format("Relationship: %s CONSUMES -> %s, already exists, incrementing total usage by 1.",
					consumer.getName(), method.getName()));
			consumeRelationship.setTotalUsage(consumeRelationship.getTotalUsage() + 1);
		} else {
			createRelationship(consumer, method);
		}
	}

	/**
	 * Row handling process method
	 * @param resultSet
	 * @throws SQLException
	 */
	public void processRow(ResultSet resultSet) throws SQLException {
		MessageMapper mapper = new MessageMapper();
		Message message= null;
		try {
			message = mapper.mapRow(resultSet);
			convertSingleMessage(message);
		} catch (Exception e) {
			log.error("Error occurred during conversion.",e);
			errors.add(new ConversionError(
					"Failed to convert message with ID: " + message!=null? message.getMsg_id(): "not available" + ". Reason: " + e.getMessage()));
		}
	}

	private List<ConversionError> persistCacheAndFinish(){
		log.info("Saving cached relations into the database.");
		relationshipRepo.save(cache.getRelations());
		CsvFileUtils.saveList(systemsList.values());
		return Lists.newArrayList(errors);
	}

	/**
	 * HELPER METHODS
	 **/

	/*
	 NEW RELATIONSHIP IN CASE IT DOESN'T EXIST YET
	  */
	private ConsumeRelationship createRelationship(Application consumer, Method method) {
		ConsumeRelationship consumeRelationship = new ConsumeRelationship(consumer, method, 1L);
		log.info(String.format("Creating new relationship: %s CONSUMES -> %s.", consumer.getName(), method.getName()));
		if (consumer.getConsumeRelationship() != null) {
			consumer.getConsumeRelationship().add(consumeRelationship);
		} else {
			consumer.setConsumeRelationship(Lists.newArrayList(consumeRelationship));
		}
		applicationRepo.save(consumer);
		cache.cacheRelation(consumeRelationship);
		return consumeRelationship;
	}

	public void setSystemsList(SystemsList systemsList) {
		this.systemsList = systemsList;
	}

}