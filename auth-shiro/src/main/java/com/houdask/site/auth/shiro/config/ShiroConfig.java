package com.houdask.site.auth.shiro.config;

import com.houdask.site.auth.shiro.manager.MyRedisCacheManager;
import com.houdask.site.auth.shiro.realm.CustomSystemCredentialsMatcher;
import com.houdask.site.auth.shiro.realm.MyShiroRealm;
import com.houdask.site.auth.shiro.session.CustomSessionListener;
import com.houdask.site.auth.shiro.session.IdGen;
import com.houdask.site.auth.shiro.session.RedisSessionDAO;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 */
@Configuration
public class ShiroConfig {

    /**
     * anon（匿名）  org.apache.shiro.web.filter.authc.AnonymousFilter
     * authc（身份验证）       org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * authcBasic（http基本验证）    org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
     * logout（退出）        org.apache.shiro.web.filter.authc.LogoutFilter
     * noSessionCreation（不创建session） org.apache.shiro.web.filter.session.NoSessionCreationFilter
     * perms(许可验证)  org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
     * port（端口验证）   org.apache.shiro.web.filter.authz.PortFilter
     * rest  (rest方面)  org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
     * roles（权限验证）  org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
     * ssl （ssl方面）   org.apache.shiro.web.filter.authz.SslFilter
     * member （用户方面）  org.apache.shiro.web.filter.authc.UserFilter
     * user  表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
     */
    /*安全认证过滤器*/
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager ) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
//        shiroFilterFactoryBean.setFilters();

        Map<String, String> filterChainDefinitionMap =  new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/userLogin", "anon");
        filterChainDefinitionMap.put("/toLogin", "anon");
        filterChainDefinitionMap.put("/hello", "authc");
        filterChainDefinitionMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

  /*  定义Shiro安全管理配置*/
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(
            @Qualifier("sessionManager") DefaultWebSessionManager sessionManager,
            @Qualifier("myRedisCacheManager") CacheManager redisCacheManager,
            @Qualifier("myShiroRealm") Realm myShiroRealm
            ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(redisCacheManager);
        securityManager.setRealm(myShiroRealm);
        return securityManager;
    }

/*
    自定义会话管理配置
*/
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getSessionManage(
            @Qualifier("shiroSessionDAO") SessionDAO sessionDAO,
            @Qualifier("sessionIdCookie") SimpleCookie sessionIdCookie ,
/*
           @Qualifier("myRedisCacheManager") CacheManager redisCacheManager,
*/
            @Qualifier("customSessionListener") CustomSessionListener customSessionListener,
            @Qualifier("sessionValidationScheduler") ExecutorServiceSessionValidationScheduler executorServiceSessionValidationScheduler
    ) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 用户信息必须是序列化格式，要不创建用户信息创建不过去，此坑很大，
        sessionManager.setSessionDAO(sessionDAO);// 如不想使用REDIS可注释此行
        Collection<SessionListener> sessionListeners = new ArrayList<>();
        sessionListeners.add(customSessionListener);
        sessionManager.setSessionListeners(sessionListeners);
        // 单位为毫秒（1秒=1000毫秒） 3600000毫秒为1个小时
        sessionManager.setSessionValidationInterval(30 * 60 * 1000);
        // 3600000 milliseconds = 1 hour
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        // 是否删除无效的，默认也是开启
        sessionManager.setDeleteInvalidSessions(true);
        // 是否开启 检测，默认开启
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationScheduler(executorServiceSessionValidationScheduler);
        // 创建会话Cookie
        sessionManager.setSessionIdCookie(sessionIdCookie);

        return sessionManager;
    }

    /**
     * 自定义session监听器
     * @return
     */
    @Bean(name = "customSessionListener")
    public CustomSessionListener customSessionListener(){
        return new CustomSessionListener();
    }

 /*	<!-- 指定本系统SESSIONID, 默认为: JSESSIONID 问题: 与SERVLET容器名冲突, 如JETTY, TOMCAT 等默认JSESSIONID,
    当跳出SHIRO SERVLET时如ERROR-PAGE容器会为JSESSIONID重新分配值导致登录会话丢失! -->*/
    @Bean(name = "sessionIdCookie")
    public SimpleCookie getSessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("hd.token");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }

 /*<!-- 自定义Session存储容器 -->*/
   @Bean(name="shiroSessionDAO")
    public  SessionDAO getSessionDAO(@Qualifier("myRedisCacheManager") CacheManager redisCacheManager,
    @Qualifier("sessionIdGenerator") SessionIdGenerator sessionIdGenerator ){
       RedisSessionDAO sessionDAO = new RedisSessionDAO();
       sessionDAO.setSessionIdGenerator(sessionIdGenerator);
       sessionDAO.setSessionPrefix("shiro-session_");
       return sessionDAO;
    }
    /*
        <!-- 会话验证调度器 -->
   */
    @Bean(name = "sessionValidationScheduler")
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(900000);
        return scheduler;
    }
    /*
    缓存管理器
*/
    @Bean(name = "myRedisCacheManager")
    public  CacheManager redisCacheManager() {
        MyRedisCacheManager cacheManager = new MyRedisCacheManager();
        return cacheManager;
    }
/*
    	<!-- AOP式方法级权限检查  -->
*/
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        attributeSourceAdvisor.setSecurityManager(securityManager );
        return attributeSourceAdvisor;
    }
    /*	<!-- 保证实现了Shiro内部lifecycle函数的bean执行 -->*/
    @Bean(name = "lifecycleBeanPostProcessor")
    public static   LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean("myShiroRealm")
    public Realm  realm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher) {
        MyShiroRealm shiroRealm= new MyShiroRealm();
        shiroRealm.setCredentialsMatcher( hashedCredentialsMatcher);
        shiroRealm.setAuthenticationCacheName("shiro-realm-");
        shiroRealm.setCachingEnabled(true);
        shiroRealm.setAuthorizationCacheName("shiro-authorization-cache-");
//        shiro_cache_com.houdask.site.auth.shiro.realm.MyShiroRealm.authorizationCache
        return shiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        CustomSystemCredentialsMatcher hashedCredentialsMatcher = new CustomSystemCredentialsMatcher("md5");
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }



}