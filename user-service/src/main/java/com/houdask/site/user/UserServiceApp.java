package com.houdask.site.user;

import com.houdask.site.common.annotation.HdSpringBootService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@HdSpringBootService("com.houdask.site.user.dao")
public class UserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}

