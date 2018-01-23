package com.houdask.site.auth.shiro.manager;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tomcat.jni.Global;
import org.springframework.util.StringUtils;
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;  
  
/** 
 * Created by Administrator on 2017/12/11. 
 * 自定义sessionId获取 
 */  
public class MySessionManager extends DefaultWebSessionManager {  
  
    private static final String AUTHORIZATION = "Authorization";  
  
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";  
  
    public MySessionManager() {  
        super();  
    }

  /*  @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 如果参数中包含“__sid”参数，则使用此sid会话。 例如：http://localhost/project?__sid=xxx&__cookie=true
        String sid = request.getParameter("__sid");
        if ( null != sid && !"".equals(sid)) {
            // 是否将sid保存到cookie，浏览器模式下使用此参数。
            if (WebUtils.isTrue(request, "__cookie")){
                HttpServletRequest rq = (HttpServletRequest)request;
                HttpServletResponse rs = (HttpServletResponse)response;
                Cookie template = getSessionIdCookie();
                Cookie cookie = new SimpleCookie(template);
                cookie.setValue(sid); cookie.saveTo(rq, rs);
            }
            // 设置当前session状态
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE); // session来源与url
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sid;
        }else{
            return super.getSessionId(request, response);
        }
    }*/


}  