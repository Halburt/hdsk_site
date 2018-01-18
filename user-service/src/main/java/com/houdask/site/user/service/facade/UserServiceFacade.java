package com.houdask.site.user.service.facade;


import com.alibaba.dubbo.config.annotation.Service;
import com.houdask.site.user.entity.User;
import com.houdask.site.user.service.HdskUserService;
import com.houdask.site.user.service.IUserServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 对外提供服务实现类 及提供者
 * @author Halburt 2018-01-16
 */
// 注册为 Dubbo 服务
@Service(version = "1.0.0")
public class UserServiceFacade  implements IUserServiceFacade {
    @Autowired
    private HdskUserService hdskUserService;
    @Value("${server.port}")
    private int port;
    public int addUser(User user) {
        try {

            return hdskUserService.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return port;
        }
    }


    @Override
    public int hello() {
        System.out.println("hello hah");

        return port;
    }


    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        return hdskUserService.findAllUser(pageNum, pageSize);
    }
}