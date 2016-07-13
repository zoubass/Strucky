package cz.zoubelu.service;

import java.sql.Timestamp;

/**
 * Created by zoubas on 10.7.16.
 */
public interface DataConversionService {

    /**
     * Convert data of specific period from relational to graph database
     * This method is scheduled to be called weekly < may vary
     */
    void convertData(Timestamp startDate, Timestamp endDate);

}
