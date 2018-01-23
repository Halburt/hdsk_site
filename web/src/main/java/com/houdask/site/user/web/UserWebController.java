package com.houdask.site.user.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.houdask.site.user.service.IUserServiceFacade;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.shiro.subject.Subject;
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

    @RequestMapping(value = "/unauth" )
    public String  login(int pageNum, int pageSize, Model model) {
        return "userLogin";
    }
    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxLogin(String username , String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
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
        return jsonObject.toString();
    }
}
