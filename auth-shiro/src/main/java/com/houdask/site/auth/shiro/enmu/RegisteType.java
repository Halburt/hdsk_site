package com.houdask.site.auth.shiro.enmu;

/**
 * 注册方式
 */
public enum RegisteType {

    /**
     *本系统内账号
     */
    USER("USER"),
    /**
     * 微博
     */
    WEIBO ("WEIBO"),
    /**
     * QQ
     */
    QQ("QQ"),
    /**
     * 微信
     */
    WECHAT("WECHAT");


    // 定义私有变量
    private String type;

    // 构造函数，枚举类型只能为私有
    private RegisteType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * 判断是否是同一type
     * @param type
     * @return
     */
    public boolean isSameWay(RegisteType type){
        return this.type.equals(type.getType());
    }


    /**
     * 判断是否是同一种type
     * @param type
     * @return
     */
    public boolean isSameWay(String type){
        return this.type.equals(type);
    }

    @Override
    public String toString() {
        return this.type;
    }
}
