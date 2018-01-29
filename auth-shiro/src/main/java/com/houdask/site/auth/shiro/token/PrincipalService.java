package com.houdask.site.auth.shiro.token;

import com.houdask.site.common.auth.base.Principal;

import java.util.Collection;

/**
 * 认证对象需要实现该service
 */
public interface PrincipalService {

    /**
     *根据 Principal 查询其所有的权限
     * @param principal
     * @return
     */
    public Collection<String> findPermissions(Principal principal);

    public SysAuthToken  login(SysAuthToken  token);

}
