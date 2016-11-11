package cz.zoubelu.cache;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;

import java.util.Collection;

/**
 * Created by t922274 on 11.11.2016.
 */
public interface Cache {

	void cacheRelation(ConsumeRelationship c);

	void cacheMethod(Method m);

	void cacheApplication(Application a);

	Collection<ConsumeRelationship> getRelations();

	Collection<Application> getApplications();

	boolean contains(Application a,Method m);

	boolean contains(SystemApp system);

	ConsumeRelationship get(Application a, Method m);

	Application get(SystemApp system);

	void clearCache();
}
