package cz.zoubelu.cache.impl;

import cz.zoubelu.cache.Cache;
import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by t922274 on 11.11.2016.
 */
@Service
public class CacheImpl implements Cache {
	private Map<String, ConsumeRelationship> relCache;

	private Map<String, Application> appCache;

	public CacheImpl() {
		relCache = new HashMap<String, ConsumeRelationship>();
		appCache = new HashMap<String, Application>();
	}

	/**
	 * Caching
	 */
	public void cacheRelation(ConsumeRelationship c) {
		relCache.put(hash(c), c);
	}

	public void cacheMethod(Method m) {
		//TODO: implementuj nebo smaz
	}

	public void cacheApplication(Application a) {
		appCache.put(hash(a), a);
	}

	/**
	 * Retrieving from cache
	 */

	public Collection<ConsumeRelationship> getRelations() {
		return relCache.values();
	}

	public Collection<Application> getApplications() {
		return appCache.values();
	}

	public ConsumeRelationship get(Application a, Method m) {
		return relCache.get(hash(a, m));
	}

	public Application get(SystemApp system) {
		return appCache.get(hash(system));
	}

	public boolean contains(Application a, Method m) {
		return relCache.containsKey(hash(a,m));
	}

	public boolean contains(SystemApp system) {
		return relCache.containsKey(hash(system));
	}

	/**
	 * Making hash
	 */
	private String hash(ConsumeRelationship c) {
		return c.getApplication().getName() + c.getApplication().getSystemId() + c.getMethod().getName() + c.getMethod()
				.getVersion();
	}

	private String hash(SystemApp system) {
		return system.getName() + system.getId();
	}

	private String hash(Application a) {
		return a.getName() + a.getId();
	}


	private String hash(Application a, Method m) {
		return a.getName() + a.getSystemId() + m.getName() + m.getVersion();
	}

	/**
	 * Clearing cache
	 * Used mainly for tests.
	 */
	public void clearCache() {
		relCache.clear();
		appCache.clear();
	}

}
