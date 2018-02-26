package com.houdask.site.common.auth.base;

import com.houdask.site.common.auth.base.enmu.LoginWay;
import com.houdask.site.common.auth.base.enmu.RegisteType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
	 * 授权用户信息
	 */
public  class Principal implements Serializable {
    public static String  Principal_SESSION_KEY = "Principal_SESSION_KEY";
    public final static String PRINCIPAL_REQUEST_KEY = "PRINCIPAL_KEY";
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

    public Principal() {
    }

    public Principal(String id, String realname, String nickname, LoginWay loginWay, RegisteType registeType) {
        this.id = id;
        this.realname = realname;
        this.nickname = nickname;
        this.loginWay = loginWay;
        this.registeType = registeType;
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

    /**
     * 获取对象map
     * @return
     */
    public Map  getMap(){
        HashMap map = new HashMap();
        map.put("id" ,this.id);
        map.put("realname" ,this.realname);
        map.put("sessionId" ,this.sessionId);
        map.put("registeType" ,this.registeType.getType());
        map.put("loginWay" ,this.loginWay.getWay());
        map.put("nickname" ,this.nickname);
        return  map;
    }

}