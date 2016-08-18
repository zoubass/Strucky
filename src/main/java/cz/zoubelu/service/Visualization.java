package cz.zoubelu.service;

import cz.zoubelu.domain.Application;

import java.util.Map;

/**
 * Created by zoubas on 14.7.2016.
 */
public interface Visualization {

	Map<String, Object> visualizeGraph();

	Map<String, Object> visualizeApplicationRelations(Application application);
}
