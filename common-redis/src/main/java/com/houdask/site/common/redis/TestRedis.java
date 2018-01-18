package com.houdask.site.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


}
