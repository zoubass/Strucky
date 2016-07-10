package cz.zoubelu.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zoubas on 10.7.16.
 */
public interface DataConversionService {

    void convertToGraphData();

}
