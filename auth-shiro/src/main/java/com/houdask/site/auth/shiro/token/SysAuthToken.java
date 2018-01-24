package com.houdask.site.auth.shiro.token;

import com.houdask.site.auth.shiro.enmu.LoginWay;
import com.houdask.site.auth.shiro.enmu.RegisteType;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * 系统用户和密码（包含验证码）令牌类
 */
public class SysAuthToken extends UsernamePasswordToken  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录终端平台
     */
    private LoginWay loginWay;

    /**
     * 等三方登录的唯一标识
     */
    private String thirdId;
    /**
     * 等三方登录方式
     */
    private RegisteType registeType;

    /**
     *
     * 授权对象（如果认证时Principal为空则进行数据校验，否则 直接认证通过。慎用！）
     *
     */
    private Principal authPrincipal;

    public SysAuthToken() {
        super();
    }

    /**
     * 账号密码登录方式
     * @param username
     * @param password
     * @param loginWay
     */
    public SysAuthToken(String username, String password, LoginWay loginWay ) {
        super(username, password);
        this.loginWay = loginWay;
        this.registeType = RegisteType.USER;
    }

    /**
     *  第三方登录
     * @param username
     * @param password
     * @param loginWay
     * @param thirdId
     */
    public SysAuthToken(String username, String password, LoginWay loginWay, RegisteType registeType, String thirdId) {
        super(username, password);
        this.loginWay = loginWay;
        this.thirdId = thirdId;
        this.registeType = registeType;
    }

    public LoginWay getLoginWay() {
        return loginWay;
    }

    public void setLoginWay(LoginWay loginWay) {
        this.loginWay = loginWay;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public RegisteType getRegisteType() {
        return registeType;
    }

    public void setRegisteType(RegisteType registeType) {
        this.registeType = registeType;
    }

    public Principal getAuthPrincipal() {
        return authPrincipal;
    }

    public void setAuthPrincipal(Principal authPrincipal) {
        this.authPrincipal = authPrincipal;
    }
}