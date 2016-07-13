package cz.zoubelu.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zoubas on 10.7.16.
 */
public interface DataConversionService {

    /**
     * Convert data of specific period from relational to graph database
     * This method is scheduled to be called weekly < may vary
     */
    //TODO: přidat období, za které vezme data?
    void convertData();

}
