package cz.zoubelu.repository;

import cz.zoubelu.domain.Message;
import cz.zoubelu.utils.TimeRange;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zoubas on 21.5.16.
 */
//FIXME: prejmenovat?
public interface InformaRepository {

    List<Message> getInteractionData(TimeRange timeRange);

}
