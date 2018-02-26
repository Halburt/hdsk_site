package com.houdask.site.auth.shiro.realm;

import com.houdask.site.auth.shiro.token.SysAuthToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
/**
 * 自定义 密码验证类
 */
public class CustomSystemCredentialsMatcher extends HashedCredentialsMatcher {


    public CustomSystemCredentialsMatcher(String hashAlgorithmName) {
        super(hashAlgorithmName);
    }

    public CustomSystemCredentialsMatcher() {
    }
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        SysAuthToken token = (SysAuthToken)authcToken;
        if(token.getAuthPrincipal() != null ){
            return true;
        }else{
            throw new UnknownAccountException("暂未实现密码认证。");
        }
	}
	
}
