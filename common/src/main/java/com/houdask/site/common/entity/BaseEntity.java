/**
 * halburt
 */
package com.houdask.site.common.entity;

import java.io.Serializable;

/**
 * Entity支持类
 */
public abstract class BaseEntity  implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */

	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;

	protected  Page page;
    private String delFlag;

	/**
	 * 自定义SQL（SQL标识，SQL内容）
	 */
//	protected Map<String, String> sqlMap;

	public BaseEntity() {
		
	}
	
	public BaseEntity(String id) {
		this();
		this.id = id;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

}
