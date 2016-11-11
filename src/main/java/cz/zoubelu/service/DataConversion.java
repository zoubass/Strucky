package cz.zoubelu.service;

import cz.zoubelu.domain.Message;
import cz.zoubelu.utils.ConversionError;
import cz.zoubelu.utils.TimeRange;

import java.util.List;

/**
 * Created by zoubas on 10.7.16.
 */
public interface DataConversion {

	/**
     * Converts relational data into nodes and relationships of graph integration, between the given time range.
     * @param timeRange Time range the relational data are in.
     * @return result with message.
     */
    List<ConversionError> convertData(String tableName, TimeRange timeRange);

	/**
	 * Converts the given messages into nodes and relationships of graph integration.
	 * @param messages List of the messages from rel. integration.
	 * @return result with message.
     */

	List<ConversionError> convertData(List<Message> messages);

	List<ConversionError> convertData(String tableName);

	void convertSingleMessage(Message message);

}
