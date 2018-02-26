package com.houdask.site.common.annotation;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
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
@SpringBootApplication
@ComponentScan("")
//@EnableCaching
public @interface HdSpringBootApi {
    /**
     * 设置Mapper文件对应的DAO类扫描目录
     */
    @AliasFor(annotation = ComponentScan.class, attribute = "value")
    String[] value() default {"com.houdask.site"}  ;
}
