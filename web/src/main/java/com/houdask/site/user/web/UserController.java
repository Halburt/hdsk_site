package com.houdask.site.user.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.houdask.site.common.web.BaseController;
import com.houdask.site.user.entity.User;
import com.houdask.site.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author Halburt 2018-01-16
 */
@RestController
public class UserController  extends BaseController{

    @Reference(version="1.0.0")
    private UserService userService;

    @RequestMapping(value = "/all" )
    public String findAllUser(int pageNum, int pageSize) {
        List list = userService.findAllUser(pageNum, pageSize);
        JSONObject json = new JSONObject();
        json.put("data", list);
        return json.toString();
    }

    @RequestMapping(value = "/add" )
    public User addUser(User user) {
        user = new User();
        user.setName(UUID.randomUUID().toString().substring(0, 4));
        user.setLoginName(UUID.randomUUID().toString().substring(0, 4));
        user.setPassword("111");
        user.setPhone(UUID.randomUUID().toString().substring(0, 4));
        userService.insert(user);
        return user;
    }
}
