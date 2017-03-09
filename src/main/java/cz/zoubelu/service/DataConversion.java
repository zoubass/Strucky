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
     *
     * @param timeRange Time range the relational data are in.
     * @param tableName Name of the table.
     * @return errors collection.
     */
    List<ConversionError> convertData(String tableName, TimeRange timeRange);

    List<ConversionError> convertData(String tableName);

    //TODO: uz to ku*** samazat
    public List<ConversionError> convertData(List<Message> messages);


}
