package com.houdask.site.auth.shiro.realm;

import com.houdask.site.common.auth.base.Principal;
import com.houdask.site.auth.shiro.token.PrincipalService;
import com.houdask.site.auth.shiro.token.SysAuthToken;
import com.houdask.site.common.spring.SpringUtil;
import org.apache.shiro.SecurityUtils;
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
        System.out.println( "登录验证....");
        SysAuthToken token = (SysAuthToken)authenticationToken;
        Principal principal = token.getAuthPrincipal() ;

        if(principal == null ){
            throw new UnknownAccountException("暂未实现密码认证。");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,"","");
        return info;
    }

    /**
    ### 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行
     *
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        info.addStringPermissions( getPrincipalService().findPermissions(principal));
        info.addStringPermission("user");
        System.out.println( "登录验证后进行权限认证...." );
        SecurityUtils.getSubject().getSession().setAttribute(Principal.Principal_SESSION_KEY,principal.getId());
        return info;
    }
    private PrincipalService getPrincipalService(){
        if(principalService == null){
            principalService = SpringUtil.getBean(PrincipalService.class);
        }
        return principalService;
    }
}