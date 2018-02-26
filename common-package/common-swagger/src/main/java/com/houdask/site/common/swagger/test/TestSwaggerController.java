package com.houdask.site.common.swagger.test;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/userTest")
@Api("userController相关api")
public class TestSwaggerController {

/**
最常用的5个注解
    @Api：修饰整个类，描述Controller的作用
    @ApiOperation：描述一个类的一个方法，或者说一个接口
    @ApiParam：单个参数描述
    @ApiModel：用对象来接收参数
    @ApiProperty：用对象接收参数时，描述对象的一个字段
*/

     /*       其它若干

    @ApiResponse：HTTP响应其中1个描述
    @ApiResponses：HTTP响应整体描述
    @ApiIgnore：使用该注解忽略这个API
    @ApiClass
    @ApiError
    @ApiErrors

    @ApiParamImplicit
    @ApiParamsImplicit*/
    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="header",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
        @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
    })
    @ApiResponses({
            @ApiResponse(response = String.class, responseContainer="List", code = 200, message = "请求成功"),
            @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")

    })

    @RequestMapping(value="/getUser",method= RequestMethod.GET)
    public String  getUser(@RequestHeader("username") String username, @RequestParam("password") String password) {
        return "{'a':'a'}";
    }

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
    })
    @ApiResponses({
            @ApiResponse(response = String.class, responseContainer="List", code = 200, message = "请求成功"),
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")

    })

    @RequestMapping(value="/getUser2",method= RequestMethod.GET)
    public String  getUser2(@RequestHeader("username") String username, @RequestParam("password") String password) {
        return "{'a':'a'}";
    }

    
}