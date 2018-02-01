package com.houdask.site.gen.dao;

import com.houdask.site.gen.entity.GenTableColumn;
import com.houdask.site.common.annotation.MyBatisDao;
import com.houdask.site.common.dao.BaseDao;

/**
 * 业务表字段DAO接口
 */
@MyBatisDao
public interface GenTableColumnDao extends BaseDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
