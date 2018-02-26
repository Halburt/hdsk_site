package com.houdask.site.common.auth.jwt;

import com.houdask.site.common.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("jwt.url")
    private String protectUrlPattern;
    @Bean
    public FilterRegistrationBean jwtFilter() {
        if(protectUrlPattern == null){
            System.err.println("JWT配置文件中未配置jwt.url");
        }else{
            System.out.println("JWT已成功配置请求"+protectUrlPattern);
        }
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter( protectUrlPattern);
        registrationBean.setFilter(filter);
        return registrationBean;
    }
}
