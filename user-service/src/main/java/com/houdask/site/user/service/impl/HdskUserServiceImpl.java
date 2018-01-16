package com.houdask.site.user.service.impl;


import com.github.pagehelper.PageHelper;
import com.houdask.site.common.service.impl.BaseServiceImpl;
import com.houdask.site.user.dao.UserMapper;
import com.houdask.site.user.entity.User;
import com.houdask.site.user.service.HdskUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务实现类
 * @author Halburt 2018-01-16
 */
@Service
public class HdskUserServiceImpl  extends BaseServiceImpl<UserMapper,User> implements HdskUserService {
//    @Autowired
//    private UserMapper dao;

    public int addUser(User user) {
        return dao.insert(user);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return dao.findAllList();
    }
}