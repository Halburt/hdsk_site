package com.houdask.site.auth.shiro.token;

import com.alibaba.fastjson.JSONObject;
import com.houdask.site.auth.shiro.enmu.LoginWay;
import com.houdask.site.auth.shiro.enmu.RegisteType;

import java.io.Serializable;

/**
	 * 授权用户信息
	 */
public  class Principal implements Serializable {
    public static String  Principal_SESSION_KEY = "Principal_SESSION_KEY";
    /**
     * 对应系统中唯一标识ID
     */
    private String id  ;
    /**
     * 姓名
     */
    private String  realname;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 登录终端平台
     */
    private LoginWay loginWay;

    /**
     * 等三方登录方式
     */
    private RegisteType registeType;

    private String sessionId ;//session id
    public Principal(String id, String realname, String nickname, LoginWay loginWay, RegisteType registeType) {
        this.id = id;
        this.realname = realname;
        this.nickname = nickname;
        this.loginWay = loginWay;
        this.registeType = registeType;
    }
    public Principal(SysAuthToken token){
        this.loginWay = token.getLoginWay();
        this.registeType = token.getRegisteType();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LoginWay getLoginWay() {
        return loginWay;
    }

    public void setLoginWay(LoginWay loginWay) {
        this.loginWay = loginWay;
    }

    public RegisteType getRegisteType() {
        return registeType;
    }

    public void setRegisteType(RegisteType registeType) {
        this.registeType = registeType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return id;
    }

}