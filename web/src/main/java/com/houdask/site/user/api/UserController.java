package com.houdask.site.user.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.houdask.site.common.web.BaseController;
import com.houdask.site.user.entity.User;
import com.houdask.site.user.service.IUserServiceFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 *  测试RestController 返回JSON
    演示duboox 消费者 Reference调用
 * @author Halburt 2018-01-16
 */
@RequestMapping(value = "/api/user" )
@RestController
public class UserController /* extends BaseController*/{

    @Reference(version = "1.0.0",check = false)
    private IUserServiceFacade userService;

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
        user.setName("我是web"+UUID.randomUUID().toString().substring(0, 4));
        user.setLoginName(UUID.randomUUID().toString().substring(0, 4));
        user.setPassword("111");
        user.setPhone(UUID.randomUUID().toString().substring(0, 4));
        userService.addUser(user);
        return user;
    }
    @RequestMapping(value = "/hello" )
    public String hello( ) {
//        userService.hello();
        return userService.hello()+"";
    }
}
