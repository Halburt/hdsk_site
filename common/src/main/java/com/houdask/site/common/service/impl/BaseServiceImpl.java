package com.houdask.site.common.service.impl;

import com.houdask.site.common.dao.BaseDao;
import com.houdask.site.common.entity.BaseEntity;
import com.houdask.site.common.entity.Page;
import com.houdask.site.common.service.BaseService;
import com.houdask.site.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
        List<T> list = dao.findList(entity);
        return list == null ? new ArrayList<T>(1) : list;
    }

    public List<T> findList(T entity ,int pageNum,int pageSize) {
        PageUtil.startPage(pageNum,pageSize,false);
        List<T> list = dao.findList(entity);
        return list == null ? new ArrayList<T>(1) : list ;
    }

    public Page<T> findPageList(T entity ) {
        PageUtil.startPage(entity.getPage().getPageNum(),entity.getPage().getPageSize());
        Page<T> page = new Page((com.github.pagehelper.Page) dao.findList(entity));
        return   page ;
    }

    /**
     * 保存方法（update、insert）
     * 根据ID有无判断
     * @param entity
     * @return
     */
    public int save(T entity) {
        if(entity.getId() !=  null){
            return  update(entity);
        }else{
            entity.preInsert();
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
