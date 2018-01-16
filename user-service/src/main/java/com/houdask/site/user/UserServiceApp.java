package com.houdask.site.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.houdask.site.user.dao")//将项目中对应的mapper类的路径加进来就可以了
@SpringBootApplication
public class UserServiceApp{

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}

