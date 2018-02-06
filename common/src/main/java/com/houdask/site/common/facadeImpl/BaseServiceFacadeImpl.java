package com.houdask.site.common.facadeImpl;

import com.houdask.site.common.entity.BaseEntity;
import com.houdask.site.common.entity.Page;
import com.houdask.site.common.facade.IBaseServiceFacade;
import com.houdask.site.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceFacadeImpl<S extends BaseService<T>,T extends BaseEntity> implements IBaseServiceFacade <T>{

    @Autowired
    protected S service;

    @Override
    public T get(String id) {
        return service.get(id);
    }

    @Override
    public T get(T entity) {
        return service.get(entity);
    }

    @Override
    public List<T> findList(T entity) {
        return service.findList(entity);
    }

    @Override
    public List<T> findList(T entity, int pageNum, int pageSize) {
        return service.findList(entity, pageNum, pageSize);
    }

    @Override
    public Page<T> findPageList(T entity ) {
        return service.findPageList(entity );
    }

    @Override
    public int save(T entity) {
        return service.save(entity);
    }

    @Override
    public int insert(T entity) {
        return service.insert(entity);
    }

    @Override
    public int update(T entity) {
        return service.update(entity);
    }

    @Override
    public int delete(T entity) {
        return service.delete(entity);
    }
}
