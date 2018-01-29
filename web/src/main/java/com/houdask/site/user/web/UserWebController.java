package com.houdask.site.user.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.houdask.site.auth.shiro.token.SysAuthToken;
import com.houdask.site.common.auth.base.Principal;
import com.houdask.site.common.auth.base.enmu.LoginWay;
import com.houdask.site.user.service.IUserServiceFacade;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.List;

/**
 * 演示 SpringBoot jsp页面
 */
@Controller
public class UserWebController {

    @Reference(version = "1.0.0" ,check = false)
    private IUserServiceFacade userService;

    @RequestMapping(value = "/user/list" )
    public String findAllUser(int pageNum, int pageSize, Model model) {
        List list = userService.findAllUser(pageNum, pageSize);
        model.addAttribute("list",list);
        return "userList";
    }

    @RequestMapping(value = "/login"  ,method = RequestMethod.GET)
    public String  login(  Model model) {
        return "userLogin";
    }

    @RequestMapping(value = "/index" )
    public String  index(  Model model) {
        model.addAttribute("hello","index");
        return "hello";
    }
    @RequiresPermissions(value="user")
    @RequestMapping(value = "/hello" )
    public String  indhelloex(  Model model) {
        model.addAttribute("hello","hello");
        return "hello";
    }
    @RequestMapping(value = "/userLogin"  ,method = RequestMethod.GET)
    public String  userLogin(  Model model) {
        return "userLogin";
    }

    @RequestMapping(value = "/toLogin" ,method = RequestMethod.POST)
    public String ajaxLogin(String username , String password, Model model ) {
        System.out.println("开始登陆哈====");
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        try {
            SysAuthToken token = new SysAuthToken(  username,   password, LoginWay.WEB );
            Principal principal =  token.newPrincipal();
            Serializable sessionId = subject.getSession(false).getId();
            System.out.println("toLogin sessionId"+ sessionId);
            principal.setSessionId(sessionId == null ? "" : sessionId.toString());
            principal.setId(username);
            principal.setRealname(username+"Realname");
            principal.setNickname(username+"Nickname");
            token.setAuthPrincipal(principal);
            subject.login(token);
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("msg", "登录成功");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("hello",jsonObject  );
        subject.checkPermissions("user");
        return "hello";
    }
}