package com.houdask.site.auth.shiro.manager;

import com.houdask.site.common.redis.base.BaseRedisDao;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义授权缓存管理类
 */
@Component
public class MyRedisCacheManager implements CacheManager {

	private String cacheKeyPrefix = "shiro_";

	/**
	 * 具体redis操作实现 在调用shiro模块  redis连接应同系统认证user连接相同
	 */
	@Autowired
	private BaseRedisDao baseRedisDao;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new MyRedisCache<K, V>(cacheKeyPrefix + name);
	}

	public String getCacheKeyPrefix() {
		return cacheKeyPrefix;
	}

	public void setCacheKeyPrefix(String cacheKeyPrefix) {
		this.cacheKeyPrefix = cacheKeyPrefix;
	}
	
	/**
	 * 自定义授权缓存管理类
	 * @author ThinkGem
	 * @version 2014-7-20
	 */
	public class MyRedisCache<K, V> implements Cache<K, V> {

		private Logger logger = LoggerFactory.getLogger(getClass());

		private String cacheKeyName = null;

		public MyRedisCache(String cacheKeyName) {
			this.cacheKeyName = cacheKeyName;
		}
		@SuppressWarnings("unchecked")
		@Override
		public V get(K key) throws CacheException {
			if (key == null){
				return null;
			}
			V value = null;
			try {
                value  = (V) baseRedisDao.getMapField(cacheKeyName,key);
 			} catch (Exception e) {
				logger.error("get {} {} {}", cacheKeyName, key,   e);
			}
			return value;
		}

		@Override
		public V put(K key, V value) throws CacheException {
			if (key == null){
				return null;
			}
			try {
                baseRedisDao.addMap(cacheKeyName,key,value);
				logger.debug("put {} {} = {}", cacheKeyName, key, value);
			} catch (Exception e) {
				logger.error("put {} {}", cacheKeyName, key, e);
			}  
			return value;
		}

		@SuppressWarnings("unchecked")
		@Override
		public V remove(K key) throws CacheException {
			V value = null;
			try {
				baseRedisDao.removeMapField(cacheKeyName,key);
				logger.debug("remove {} {}", cacheKeyName, key);
			} catch (Exception e) {
				logger.warn("remove {} {}", cacheKeyName, key, e);
			}
			return value;
		}

		@Override
		public void clear() throws CacheException {
			try {
				baseRedisDao.remove(cacheKeyName);
				logger.debug("clear {}", cacheKeyName);
			} catch (Exception e) {
				logger.error("clear {}", cacheKeyName, e);
			}
		}

		@Override
		public int size() {
			int size = 0;
			try {
				baseRedisDao.getMapSize(cacheKeyName);
				logger.debug("size {} {} ", cacheKeyName, size);
				return size;
			} catch (Exception e) {
				logger.error("clear {}",  cacheKeyName, e);
			}
			return size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Set<K> keys() {
			Set<K> keys = null;
			try {
				keys =	baseRedisDao.getMapFieldKey(cacheKeyName);
				logger.debug("keys {} {} ", cacheKeyName, keys);
				return keys;
			} catch (Exception e) {
				logger.error("keys {}", cacheKeyName, e);
				keys = new HashSet<>();
			}
			return keys;
		}
		@SuppressWarnings("unchecked")
		@Override
		public Collection<V> values() {
			Collection<V> vals = null ;
			try {
				vals = baseRedisDao.getMapFieldValue(cacheKeyName);
				logger.debug("values {} {} ", cacheKeyName, vals);
				return vals;
			} catch (Exception e) {
				logger.error("values {}",  cacheKeyName, e);
				vals = Collections.emptyList();
			}
			return vals;
		}
	}
}