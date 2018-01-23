package com.houdask.site.common.auth.base.service;

import com.houdask.site.common.auth.base.AuthEntity;

/**
 *
 * 定义AuthEntity的认证相关接口
 *
 */
public interface AuthEntityService {

    public boolean checkAuth(AuthEntity entity);
//    public boolean checkAuth(AuthEntity entity);

}
