package com.houdask.site.user.dao;

import com.houdask.site.common.annotation.MyBatisDao;
import com.houdask.site.common.dao.BaseDao;
import com.houdask.site.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@MyBatisDao
public interface UserMapper extends BaseDao<User> {
    int deleteByPrimaryKey(String id);

//    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}