package com.houdask.site.common.config;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.houdask.site.common.entity.BaseEntity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * dubbox序列化实现类
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

        public Collection<Class> getSerializableClasses() {
            List<Class> classes = new LinkedList<Class>();
            classes.add(BaseEntity.class);
            return classes;
        }
}