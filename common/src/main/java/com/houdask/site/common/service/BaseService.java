package com.houdask.site.common.service;

import com.houdask.site.common.dao.BaseDao;
import com.houdask.site.common.entity.BaseEntity;

import java.util.List;

/**
 * @author Halburt 2018-01-16
 */
public interface BaseService< T extends BaseEntity> {

    /**
     * 获取单条数据
     * @param T
     * @return
     */
    public T get(String id);


    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public T get(T entity);

    /**
     * 查询列表数据
     * @param entity
     * @return
     */
    public List<T> findList(T entity);

    /**
     * 保存数据
     */
    public int insert(T entity) ;

    public int update(T entity) ;

}
