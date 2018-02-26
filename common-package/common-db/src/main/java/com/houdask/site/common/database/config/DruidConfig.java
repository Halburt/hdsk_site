package com.houdask.site.common.database.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DruidConfig {

    @Value("${spring.datasource.druid.url}")
    private String druidUrl;
    @Value("${spring.datasource.druid.allow}")
    private String allow;
    @Value("${spring.datasource.druid.deny}")
    private String deny;
    @Value("${spring.datasource.druid.loginUsername}")
    private String loginUsername;
    @Value("${spring.datasource.druid.loginPassword}")
    private String druidPassword;
    @Value("${spring.datasource.druid.resetEnable}")
    private String resetEnable;
    /**
     * 注册一个StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServle2(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),druidUrl);

        //添加初始化参数：initParams
        /** 白名单，如果不配置或value为空，则允许所有 */
        servletRegistrationBean.addInitParameter("allow",allow);
        /** 黑名单，与白名单存在相同IP时，优先于白名单 */
        servletRegistrationBean.addInitParameter("deny",deny);
        /** 用户名 */
        servletRegistrationBean.addInitParameter("loginUsername",loginUsername);
        /** 密码 */
        servletRegistrationBean.addInitParameter("loginPassword",druidPassword);
        /** 禁用页面上的“Reset All”功能 */
        servletRegistrationBean.addInitParameter("resetEnable",resetEnable);
        return servletRegistrationBean;
    }

    /**
     * 注册一个：WebStatFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter2(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        /** 过滤规则 */
        filterRegistrationBean.addUrlPatterns("/*");
        /** 忽略资源 */
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
