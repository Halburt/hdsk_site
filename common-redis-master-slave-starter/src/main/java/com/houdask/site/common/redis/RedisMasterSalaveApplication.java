package com.houdask.site.common.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.houdask.site")
@SpringBootApplication
public class RedisMasterSalaveApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisMasterSalaveApplication.class, args);
    }
}
