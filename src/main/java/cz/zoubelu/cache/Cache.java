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

	void cacheApplication(Application a);

	void cacheMethod(Application provider, Method m);

	boolean contains(Application consumer,Method m);

	boolean contains(SystemApp system);

	boolean contains(Application provider, String methodName, Integer methodVersion);

	ConsumeRelationship get(Application consumer, Method m);

	Application get(SystemApp systemApp);

	Method get(Application provider, String methodName, Integer methodVersion);

	Collection<ConsumeRelationship> getRelations();

	void clearCache();
}
