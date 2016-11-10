package cz.zoubelu.repository;

import cz.zoubelu.utils.TimeRange;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * Created by zoubas on 21.5.16.
 */
public interface InformaMessageRepository {

	/**
	 *
	 *
	 * @param timeRange
	 * @return messages
	 */
	void fetchAndConvertData(String tableName, TimeRange timeRange, RowCallbackHandler handler);

	/**
	 *
	 *
	 * @param tableName
	 * @return messages
	 */

	void fetchAndConvertData(String tableName, RowCallbackHandler handler);
}
