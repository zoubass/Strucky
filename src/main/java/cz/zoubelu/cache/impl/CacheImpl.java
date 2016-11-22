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

	public CacheImpl() {
		relCache = new HashMap<String, ConsumeRelationship>();
	}

	/**
	 * Caching
	 */
	public void cacheRelation(ConsumeRelationship c) {
		relCache.put(hash(c), c);
	}

	/**
	 * Retrieving from cache
	 */

	public Collection<ConsumeRelationship> getRelations() {
		return relCache.values();
	}

	public ConsumeRelationship get(Application a, Method m) {
		return relCache.get(hash(a, m));
	}

	public boolean contains(Application a, Method m) {
		return relCache.containsKey(hash(a,m));
	}

	/**
	 * Making hash
	 */
	private String hash(ConsumeRelationship c) {
		return c.getApplication().getName() + c.getApplication().getSystemId() + c.getMethod().getName() + c.getMethod()
				.getVersion();
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
	}

}
