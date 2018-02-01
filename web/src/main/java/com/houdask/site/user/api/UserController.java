package com.houdask.site.user.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.houdask.site.common.web.BaseController;
import com.houdask.site.user.entity.User;
import com.houdask.site.user.service.IUserServiceFacade;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 *  测试RestController 返回JSON
    演示duboox 消费者 Reference调用
 * @author Halburt 2018-01-16
 */
@RequestMapping(value = "${adminPath}/api/user" )
@RestController
@ApiModel
public class UserController /* extends BaseController*/{

    @Reference(version = "1.0.0",check = false)
    private IUserServiceFacade userService;

    @ApiOperation("获取所有用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="pageNum",dataType="String",required=true,value="页码",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="pageSize",dataType="String",required=true,value="每页页数",defaultValue="2")
    })
    @ApiResponses({
            @ApiResponse(response = User.class, responseContainer="List", code = 200, message = "请求成功"),
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")

    })
    @RequestMapping(value = "/all" )
    public List findAllUser(int pageNum, int pageSize) {
        List list = userService.findAllUser(pageNum, pageSize);
        return list;
    }

    @ApiOperation("添加用户信息")
    @ApiResponses({
            @ApiResponse(response = User.class, code = 200, message = "请求成功"),
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")

    })
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
