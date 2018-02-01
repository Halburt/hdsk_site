package com.houdask.site.user.service.impl;


import com.github.pagehelper.PageHelper;
import com.houdask.site.common.redis.base.BaseRedisDao;
import com.houdask.site.common.service.impl.BaseServiceImpl;
import com.houdask.site.user.dao.UserMapper;
import com.houdask.site.user.entity.User;
import com.houdask.site.user.service.HdskUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 业务实现类
 * @author Halburt 2018-01-16
 */
@Service
public class HdskUserServiceImpl  extends BaseServiceImpl<UserMapper,User> implements HdskUserService {

    @Autowired
    private  BaseRedisDao baseRedisDao;

    /**
     * 获取redis中数据
     * @return
     */
    public Map getCacheUser( ) {

        return baseRedisDao.getMap("user");
    }
    /*
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * unless 表示条件表达式成立的话不放入缓存
     */

    public int addUser(User user) {
        baseRedisDao.addMap("user",user.getId(),user,5000);
        return dao.insert(user);
    }

    @Override
//    @CachePut(value = RedisKeys._CACHE_TEST, key ="#id" )
//    @Cacheable(value = RedisKeys._CACHE_TEST, key = "2")
    public User get(String id) {

        return dao.get(id);
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