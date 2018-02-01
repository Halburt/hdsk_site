package com.houdask.site.gen.dao;

import com.houdask.site.gen.entity.GenTableColumn;
import com.houdask.site.gen.entity.GenTable;
import com.houdask.site.common.annotation.MyBatisDao;
import com.houdask.site.common.dao.BaseDao;

import java.util.List;

/**
 * 业务表字段DAO接口
 *  @author by Halburt
 */
@MyBatisDao
public interface GenDataBaseDictDao extends BaseDao<GenTableColumn> {

	/**
	 * 查询表列表
	 * @param genTable
	 * @return
	 */
	List<GenTable> findTableList(GenTable genTable);

	/**
	 * 获取数据表字段
	 * @param genTable
	 * @return
	 */
	List<GenTableColumn> findTableColumnList(GenTable genTable);
	
	/**
	 * 获取数据表主键
	 * @param genTable
	 * @return
	 */
	List<String> findTablePK(GenTable genTable);
	
}
