package com.houdask.site.common.cache;

        import org.springframework.cache.CacheManager;
        import org.springframework.cache.annotation.EnableCaching;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.data.redis.cache.RedisCacheManager;
        import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate){
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        System.out.println("CacheManager初始化====="+(redisTemplate == null));
        return cacheManager;
    }
}
