package com.houdask.site.gen.dao;

import com.houdask.site.common.annotation.MyBatisDao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现对非预编译的数据查询
 */
@MyBatisDao
public interface GenTabelDataDao   {
	
	/**
	 * 根据where条件查询table的某几列数据
	 * @param tableName
	 * @param cols
	 * @param whereStr
	 * @return
	 */
	public List<Map<String, Object>> queryList(String tableName, String colName, String whereStr);

	public List<Map<String, Object>> queryList(Map map);
	
	public List<LinkedHashMap<String, Object>> queryListData(Map map);
	
	public List<LinkedHashMap<String, Object>> queryOrderListData(Map map);
}
