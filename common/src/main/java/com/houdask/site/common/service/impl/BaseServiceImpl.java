package com.houdask.site.common.service.impl;

import com.houdask.site.common.dao.BaseDao;
import com.houdask.site.common.entity.BaseEntity;
import com.houdask.site.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * @author Halburt 2018-01-16
 */
public abstract class BaseServiceImpl<D extends BaseDao<T> ,T extends  BaseEntity> implements BaseService<T>  {
    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    public T get(String id) {
        return dao.get(id);
    }

    public T get(T entity) {
        return dao.get(entity);
    }

    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    public int save(T entity) {
        if(entity.getId() !=  null){
            return  update(entity);
        }else{
            return  insert(entity);
        }

    }

    public int insert(T entity) {
        return dao.insert(entity);
    }

    public int update(T entity) {
        return dao.update(entity);
    }

    public int delete(T entity) {
        return dao.delete(entity);
    }

    public int delete(String id) {
        return dao.delete(id);
    }

}
