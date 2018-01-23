package com.houdask.site.common.auth.base;


import java.io.Serializable;

/**
 * 定义安全认证统一接口
 *
 * 为了方便安全认证不同的实现，以及解耦
 * 故将认证对象的基类定义为interface
 */
public interface AuthEntity extends Serializable {

//    private String id;
//    private String password;
//    private String name;
//    private String status;

    public String getId() ;

    public String getPassword() ;

    public String getName() ;

    public String getStatus();

    public void setId(String id);

    public void setPassword(String password) ;

    public void setName(String name);

    public void setStatus(String status) ;
}
