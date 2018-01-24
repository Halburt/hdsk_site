package com.houdask.site.auth.shiro.realm;

import com.houdask.site.auth.shiro.token.Principal;
import com.houdask.site.auth.shiro.token.PrincipalService;
import com.houdask.site.auth.shiro.token.SysAuthToken;
import com.houdask.site.common.spring.SpringUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义shiro安全认证
 */
public class MyShiroRealm extends AuthorizingRealm {

    private final static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    private PrincipalService  principalService;

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SysAuthToken token = (SysAuthToken)authenticationToken;
        System.out.println(token);
        Principal principal = token.getAuthPrincipal() ;

        if(principal == null ){
            throw new UnknownAccountException("暂未实现密码认证。");
        }
        System.out.println(principal);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,"","");
        return info;
    }
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     * 设置权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println( "登录验证后进行权限认证....");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        info.addStringPermissions( getPrincipalService().findPermissions(principal));
        info.addStringPermission("user");
        return info;
    }
    private PrincipalService getPrincipalService(){
        if(principalService == null){
            principalService = SpringUtil.getBean(PrincipalService.class);
        }
        return principalService;
    }

}