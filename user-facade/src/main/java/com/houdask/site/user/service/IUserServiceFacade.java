package com.houdask.site.user.service;

import com.houdask.site.common.service.facade.IBaseServiceFacade;
import com.houdask.site.user.entity.User;

import java.util.List;

/**
 * 对外提供服务接口
 * @author Halburt 2018-01-16
 */
public interface    IUserServiceFacade  extends IBaseServiceFacade<User>{
    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    public abstract List<User> findAllUser(int pageNum, int pageSize);

    public abstract int addUser(User user);
}
