package cz.zoubelu.service;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;

/**
 * Created by t922274 on 8.11.2016.
 */
public interface DynamicEntityProvider {

	cz.zoubelu.domain.Application getApplication(SystemApp system);

	Method getConsumedMethod(cz.zoubelu.domain.Application app, Message m);

	void createConsumeRelation(cz.zoubelu.domain.Application a,Method m);

	void persistCachedRelations();
}
