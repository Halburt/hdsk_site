package com.houdask.site.user.web;

import com.alibaba.fastjson.JSONObject;
import com.houdask.site.common.web.BaseController;
import com.houdask.site.user.entity.User;
import com.houdask.site.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * 用户模块测试类  每个Service模块都可以单独运行
 * @author Halburt 2018-01-16
 */
@RestController
public class UserController extends BaseController{

    @Autowired
    @Qualifier(value = "userService1")
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
        user.setId(UUID.randomUUID().toString().substring(0, 4));
        user.setName(UUID.randomUUID().toString().substring(0, 4));
        user.setLoginName(UUID.randomUUID().toString().substring(0, 4));
        user.setPassword("111");
        user.setPhone(UUID.randomUUID().toString().substring(0, 4));
        userService.insert(user);
        return user;
    }
}
