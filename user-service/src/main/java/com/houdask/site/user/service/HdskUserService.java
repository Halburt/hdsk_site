package com.houdask.site.user.service;

import com.houdask.site.common.service.BaseService;
import com.houdask.site.user.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 业务service接口
 * @author Halburt 2018-01-16
 */

public interface HdskUserService extends BaseService<User>  {
    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    public List<User> findAllUser(int pageNum, int pageSize);

    public int addUser(User user);

    public Map getCacheUser( );
}
