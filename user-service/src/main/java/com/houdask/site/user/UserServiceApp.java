package com.houdask.site.user;

import com.houdask.site.common.annotation.HdSpringBootService;
import org.springframework.boot.SpringApplication;
@HdSpringBootService("com.houdask.site.user.dao")
public class UserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }


}

