package cz.zoubelu.service;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.domain.Application;

/**
 * Created by t922274 on 8.11.2016.
 */
public interface DynamicEntityProvider {

	Application getApplication(SystemApp system);
}
