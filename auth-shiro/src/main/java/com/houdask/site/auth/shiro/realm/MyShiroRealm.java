package com.houdask.site.auth.shiro.realm;

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
    
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.debug("登录验证后进行权限认证....");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.debug("登录操作进行登录认证......");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

     /*   AppUser user = SpringContextUtils.getBean(AppUserServiceImpl.class).getUserByName(token.getUsername());
        if (user == null) {
            // 没找到帐号
            throw new UnknownAccountException(
                    "没有在本系统中找到对应的用户信息。");
        }*/
        //简单验证
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
//                user.getUsername(),user.getPassword(),getName());
        
        return info;
    }

}