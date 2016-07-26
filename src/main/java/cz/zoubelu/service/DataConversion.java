package cz.zoubelu.service;

import cz.zoubelu.domain.Message;
import cz.zoubelu.utils.ConversionResult;
import cz.zoubelu.utils.TimeRange;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zoubas on 10.7.16.
 */
public interface DataConversion {

	/**
     *
     * @param timeRange
     * @return
     */
    ConversionResult convertData(TimeRange timeRange);

	ConversionResult convertData(List<Message> messages);
}
