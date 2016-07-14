package cz.zoubelu.repository;

import cz.zoubelu.domain.Message;
import cz.zoubelu.utils.TimeRange;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zoubas on 21.5.16.
 */
//FIXME: prejmenovat?
public interface InformaDao {

    List<Message> getInteractionData(TimeRange timeRange);

    //TODO: tato metoda je zde pouze pro test, je pro vytvoření struktury grafové databáze
    List<String> getApplicationsInPlatform();

    List<String> getMethodOfApplication(String application);

}
