package com.houdask.site.common.cache;

import com.houdask.site.common.redis.config.RedisConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Spring缓存管理配置
 */
@Configuration
public class SpringCacheConfig extends   CachingConfigurerSupport{

    @Value("cache.names")
    private String cacheNames;

/*    @Bean
    public CacheManager cacheManager( RedisTemplate<?,?>  redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(60 * 30); // 30min
        return cacheManager;
    }*/
}
