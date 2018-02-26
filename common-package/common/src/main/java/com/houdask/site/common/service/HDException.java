/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.houdask.site.common.service;

/**
 * Service层公用的Exception,
 */
public class HDException extends RuntimeException {

	private String code;
	private String msg;

	public HDException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return  msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
