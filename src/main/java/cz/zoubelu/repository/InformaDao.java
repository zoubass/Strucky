package cz.zoubelu.repository;

import cz.zoubelu.domain.InformaLog;

import java.util.List;

/**
 * Created by zoubas on 21.5.16.
 */
public interface InformaDao {

    List<InformaLog> getInteractionData(/* Časové rozmezí */);

}
