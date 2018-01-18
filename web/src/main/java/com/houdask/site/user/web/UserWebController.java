package com.houdask.site.user.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.houdask.site.user.service.IUserServiceFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
