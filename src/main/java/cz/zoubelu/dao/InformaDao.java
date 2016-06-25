package cz.zoubelu.dao;

import cz.zoubelu.model.InformaLog;

import java.util.List;

/**
 * Created by zoubas on 21.5.16.
 */
public interface InformaDao {

    List<InformaLog> getInteractionData(/* Časové rozmezí */);

}
