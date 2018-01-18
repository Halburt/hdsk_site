package com.houdask.site.user.web;

import com.alibaba.fastjson.JSONObject;
import com.houdask.site.common.web.BaseController;
import com.houdask.site.user.entity.User;
import com.houdask.site.user.service.HdskUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 用户模块测试类  每个Service模块都可以单独运行
 * @author Halburt 2018-01-16
 */
@RestController
public class UserTestController extends BaseController{
    @Autowired
    private HdskUserService hdskUserService;


    @RequestMapping(value = "/user/all" )
    public String findAllUser(int pageNum, int pageSize) {
        List list = hdskUserService.findAllUser(pageNum, pageSize);
        JSONObject json = new JSONObject();//userService.get("1")
        json.put("data", list);
        return json.toString();
    }

    @RequestMapping(value = "/user/add" )
    public User addUser(User user) {
        user = new User();
        user.setId(UUID.randomUUID().toString().substring(0, 4));
        user.setName(UUID.randomUUID().toString().substring(0, 4));
        user.setLoginName(UUID.randomUUID().toString().substring(0, 4));
        user.setPassword("111");
        user.setPhone(UUID.randomUUID().toString().substring(0, 4));
        hdskUserService.addUser(user);

        return user;
    }
    @RequestMapping("/user/cache")
    public String getCacheUser(){
        Map list =  hdskUserService.getCacheUser( );
        JSONObject json = new JSONObject();//userService.get("1")
        json.put("data", list);
        return json.toString();
    }
}
