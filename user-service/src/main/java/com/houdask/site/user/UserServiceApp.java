package com.houdask.site.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
@EnableAutoConfiguration
@MapperScan("com.houdask.site.user.dao")//将项目中对应的mapper类的路径加进来就可以了
@SpringBootApplication
@ComponentScan("com.houdask.site")
public class UserServiceApp extends SpringBootServletInitializer {
/*
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UserServiceApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}*/
//jar包

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}

