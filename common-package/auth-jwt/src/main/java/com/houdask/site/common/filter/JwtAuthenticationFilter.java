package com.houdask.site.common.filter;

import com.houdask.site.common.auth.base.Principal;
import com.houdask.site.common.auth.jwt.JwtUtil;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT过滤器 校验JWT认证用户
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String protectUrlPattern;
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthenticationFilter(String protectUrlPattern) {
        this.protectUrlPattern = protectUrlPattern;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(pathMatcher.match(protectUrlPattern, request.getServletPath())) {
                String token = request.getHeader(JwtUtil.TOKEN_KEY);
                Principal principal  = JwtUtil.getPrincipalJWT(token);
                if(principal == null){
                    throw new Exception();
                }else{
//                  向request中添加认证用户信息
                    request.setAttribute(Principal.PRINCIPAL_REQUEST_KEY,principal);
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

}