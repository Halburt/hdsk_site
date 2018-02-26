package com.houdask.site.common.auth.base.enmu;

/**
 * 登录方式
 */
public enum LoginWay {
    /**
     * web端登录（pc网页版）
     */
    WEB("WEB"),
    /**
     * wap端登录 (手机网页版)
     */
    WAP("WAP"),
    /**
     * IOS - APP端登录
     */
    IOS ("IOS"),
    /**
     * Android 端登录
     */
    ANDROID("ANDROID"),
    /**
     * 微信服务号端登录
     */
    WXMP("WXMP"),
    /**
     * 微信企业号端登录
     */
    WXCP("WXCP");

    // 定义私有变量
    private String way;

    // 构造函数，枚举类型只能为私有
    private LoginWay(String way) {
        this.way = way;
    }

    public String getWay() {
        return way;
    }

    /**
     * 判断是否是同一种登录方式
     * @param way
     * @return
     */
    public boolean isSameWay(LoginWay way){
        return this.way.equals(way.getWay());
    }


    /**
     * 判断是否是同一种登录方式
     * @param way
     * @return
     */
    public boolean isSameWay(String way){
        return this.way.equals(way);
    }

    @Override
    public String toString() {
        return this.way;
    }
}
