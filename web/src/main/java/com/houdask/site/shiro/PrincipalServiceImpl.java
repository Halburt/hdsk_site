package com.houdask.site.shiro;

import com.houdask.site.common.auth.base.Principal;
import com.houdask.site.auth.shiro.token.PrincipalService;
import com.houdask.site.auth.shiro.token.SysAuthToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PrincipalServiceImpl implements PrincipalService {
    @Override
    public Collection<String> findPermissions(Principal principal) {
        return new ArrayList<>();
    }

    @Override
    public SysAuthToken login(SysAuthToken token) {
        return null;
    }
}
