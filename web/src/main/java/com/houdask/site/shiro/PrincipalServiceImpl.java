package com.houdask.site.shiro;

import com.houdask.site.auth.shiro.token.Principal;
import com.houdask.site.auth.shiro.token.PrincipalService;
import com.houdask.site.auth.shiro.token.SysAuthToken;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PrincipalServiceImpl implements PrincipalService {
    @Override
    public Collection<String> findPermissions(Principal principal) {
        return null;
    }

    @Override
    public SysAuthToken login(SysAuthToken token) {
        return null;
    }
}
