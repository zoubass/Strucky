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

	private final Map<String, ConsumeRelationship> relCache;
	private final Map<String, Application> appCache;
	private final Map<String, Method> methodCache;

	/**
	 * CONSTRUCTOR
	 */

	public CacheImpl() {
		relCache = new HashMap<String, ConsumeRelationship>();
		appCache = new HashMap<String, Application>();
		methodCache = new HashMap<String, Method>();
	}

	/**
	 * CACHING
	 */
	public void cacheRelation(ConsumeRelationship c) {
		relCache.put(hash(c), c);
	}

	public void cacheApplication(Application a) {
		appCache.put(hash(a), a);
	}

	public void cacheMethod(Application provider, Method m) {
		methodCache.put(hash(provider, m.getName(), m.getVersion()), m);
	}

	/**
	 * RETRIEVING FROM CACHE
	 */

	public Collection<ConsumeRelationship> getRelations() {
		return relCache.values();
	}

	public ConsumeRelationship get(Application a, Method m) {
		return relCache.get(hash(a, m));
	}

	public Application get(SystemApp system) {
		return appCache.get(hash(system));
	}

	public Method get(Application provider, String methodName, Integer methodVersion) {
		return methodCache.get(hash(provider, methodName, methodVersion));
	}

	/**
	 * CONTAINS
	 */

	public boolean contains(Application a, Method m) {
		return relCache.containsKey(hash(a, m));
	}

	public boolean contains(SystemApp system) {
		return appCache.containsKey(hash(system));
	}

	public boolean contains(Application provider, String methodName, Integer methodVersion) {
		return methodCache.containsKey(hash(provider, methodName, methodVersion));
	}

	/**
	 * HASH MAKING METHODS
	 */
	private String hash(ConsumeRelationship c) {
		return c.getApplication().getName() + c.getApplication().getSystemId() + c.getMethod().getName() + c.getMethod()
				.getVersion();
	}

	private String hash(Application a, Method m) {
		return a.getName() + a.getSystemId() + m.getName() + m.getVersion();
	}

	private String hash(SystemApp s) {
		return s.getName() + s.getId();
	}

	private String hash(Application a) {
		return a.getName() + a.getSystemId();
	}

	private String hash(Application p, String name, Integer version) {
		return p.getName() + p.getSystemId() + name + version.toString();
	}

	/**
	 * CLEARING CACHE
	 * Used only for tests.
	 */
	public void clearCache() {
		relCache.clear();
		appCache.clear();
		methodCache.clear();
	}

}
