package com.houdask.site.sys.user;

/**
 * 定义需要登录角色的基类
 */
public interface LoginUser  {

//    private String id;
//    private String password;
//    private String name;
//    private String status;

    public String getId() ;

    public String getPassword() ;

    public String getName() ;

    public String getStatus();
}
