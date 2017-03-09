package cz.zoubelu.service;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;

/**
 * Created by t922274 on 8.11.2016.
 */
public interface DynamicEntityProvider {

	Application getApplication(SystemApp system);

	Method getConsumedMethod(Application app, Message m);

	void createConsumeRelation(Application a,Method m);

	void persistCachedRelations();
}
