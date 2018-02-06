package com.houdask.site.common.facade;

import com.houdask.site.common.entity.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Halburt 2018-01-16
 */
public interface IBaseServiceFacade<T> {

    /**
     * 获取单条数据
     * @param id
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
     * 分页查询（不会统计记录总数）
     * @param entity
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<T> findList(T entity, int pageNum, int pageSize);

    /**
     * 分页查询（会统计记录总数信息 在Page对象里）
     * @param entity
     * @return
     */
    public Page<T> findPageList(T entity );

    /**
     * 保存数据
     */
    public int save(T entity) ;
    /**
     * 保存数据
     */
    public int insert(T entity) ;

    public int update(T entity) ;
    /**
     * 删除
     * @param entity
     * @return
     */
    public int delete(T entity) ;
}
