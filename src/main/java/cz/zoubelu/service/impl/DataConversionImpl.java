package cz.zoubelu.service.impl;

import com.google.common.collect.Lists;
import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.codelist.SystemsList;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.InformaMessageRepository;
import cz.zoubelu.repository.mapper.MessageMapper;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.service.DynamicEntityProvider;
import cz.zoubelu.utils.ConversionError;
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
//TODO: Cela trida by nemela byt asi transactional, je pak nutne nastavit obri timneout na neo4j databazi, aby neuzavrela connection
@Transactional
public class DataConversionImpl implements DataConversion, RowCallbackHandler{
	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	private Validator validator;

	@Autowired
	private DynamicEntityProvider provider;

	@Autowired
	private ConversionThread conversionThread;

	@Autowired
	private InformaMessageRepository informaRepository;

	private SystemsList systemsList;

	private final Set<ConversionError> errors;

	/**
	 * CONSTRUCTOR
	 */
	public DataConversionImpl(){
		errors = new HashSet<ConversionError>();
	}

	/**
	 * Interface method implementation
	 */
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
	 * ROW HANDLING PROCESS METHOD
	 *
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
					"Failed to convert message with ID: " + message!=null? message.getMsg_id(): "N/A" + ". Reason: " + e.getMessage()));
		}
	}

	/**
	 * Main method for mapping the message onto graph.
	 * @param msg
	 */
	public void convertSingleMessage(Message msg) {
		validator.validateMessage(msg);

		Application providingApp = getProvidingApplication(msg);
		Method consumedMethod = provider.getConsumedMethod(providingApp, msg);
		Application consumingApp = getConsumingApplication(msg);

		log.info(String.format("Saving relationship - Provider: %s, Method: %s, Consumer: %s.", providingApp.getName(),
				consumedMethod.getName(), consumingApp.getName()));
		provider.createConsumeRelation(consumingApp,consumedMethod);
	}

	private Application getProvidingApplication(Message msg) {
		SystemApp system = systemsList.getIdByName(msg.getApplication());
		boolean isNewlyCreated = (3000 < system.getId()) && (system.getId() < 5000);
		if (isNewlyCreated) {
			if (msg.getMsg_tar_sys() != null && !msg.getMsg_tar_sys().equals(0)) {
				system.setId(msg.getMsg_tar_sys());
			} else {
				log.debug(String.format("Added new system w. name: %s, and generated system ID: %s",system.getName(),system.getId()));
			}
		}
		return provider.getApplication(system);
	}

	private Application getConsumingApplication(Message msg) {
		SystemApp system = systemsList.getSystemByID(msg.getMsg_src_sys());
		return provider.getApplication(system);
	}


	private List<ConversionError> persistCacheAndFinish(){
		provider.persistCachedRelations();
//		CsvFileUtils.saveList(systemsList.values());
		return Lists.newArrayList(errors);
	}

	public void setSystemsList(SystemsList systemsList) {
		this.systemsList = systemsList;
	}

}