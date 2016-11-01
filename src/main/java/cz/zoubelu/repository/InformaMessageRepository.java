package cz.zoubelu.repository;

import cz.zoubelu.domain.Message;
import cz.zoubelu.utils.TimeRange;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zoubas on 21.5.16.
 */
public interface InformaMessageRepository {


	/**
     * Returns all interaction messages within the specified timeRange
     * @param timeRange
     * @return messages
     */
    List<Message> getInteractionData(TimeRange timeRange);

	/**
	 * Return all interaction messages from the whole table (which is one month)
	 * @param tableName
	 * @return messages
	 */
	List<Message>  getInteractionData(String tableName);
}
