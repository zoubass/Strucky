package cz.zoubelu.repository;

import cz.zoubelu.domain.Message;

import java.util.List;

/**
 * Created by zoubas on 21.5.16.
 */
//FIXME: prejmenovat?
public interface InformaDao {

    List<Message> getInteractionData(/* Časové rozmezí */);

    //TODO: tato metoda je zde pouze pro test, je pro vytvoření struktury grafové databáze
    List<String> getApplicationsInPlatform();

    List<String> getMethodOfApplication(String application);

}
