package com.houdask.site.common.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 封装service启动类的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableAutoConfiguration
@MapperScan("")
@SpringBootApplication
@ComponentScan
@EnableCaching
public @interface HdSpringBootService {
    /**
     * 设置Mapper文件对应的DAO类扫描目录
     */
    @AliasFor(annotation = MapperScan.class, attribute = "value")
    String[] value() default {};
}
