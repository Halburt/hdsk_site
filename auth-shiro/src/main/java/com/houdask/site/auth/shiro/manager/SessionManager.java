/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.houdask.site.auth.shiro.manager;

import com.houdask.site.auth.shiro.session.IdGen;
import com.houdask.site.auth.shiro.util.Servlets;
import com.houdask.site.common.utils.StringUtils;
import org.apache.shiro.session.*;
import org.apache.shiro.session.mgt.DelegatingSession;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 自定义WEB会话管理类
 */
public class SessionManager extends DefaultWebSessionManager {

	public SessionManager() {
		super();
	}
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

        // 如果参数中包含“__sid”参数，则使用此sid会话。 例如：http://localhost/project?__sid=xxx&__cookie=true
		String sid = request.getParameter("__sid");
		if (StringUtils.isNotBlank(sid)) {
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
            Serializable  sid1 = super.getSessionId(request, response);
            System.out.println("getSessionId==="+sid1.toString());
			return  sid1;
		}
	}


    protected Session retrieveSession(SessionKey sessionKey) {
        System.out.println("retrieveSession ==sessionKey:" + sessionKey);
		try{
			return super.retrieveSession(sessionKey);
		}catch (NullPointerException e) {
    		// 获取不到SESSION不抛出异常
			return null;
		}catch (InvalidSessionException e1) {
			// 获取不到SESSION不抛出异常
			return null;
		}
	}

    public Date getStartTimestamp(SessionKey key) {
    	try{
    		return super.getStartTimestamp(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public Date getLastAccessTime(SessionKey key) {
    	try{
    		return super.getLastAccessTime(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public long getTimeout(SessionKey key){
    	try{
    		return super.getTimeout(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return 0;
		}
    }

    public void setTimeout(SessionKey key, long maxIdleTimeInMillis) {
    	try{
    		super.setTimeout(key, maxIdleTimeInMillis);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }

    public void touch(SessionKey key) {
        System.out.println("touch key==="+key);
    	try{
    		Session session = getSession( key);
    		if (session != null ) {
    			HttpServletRequest request = Servlets.getRequest();
    			if (request != null){
    				String uri = request.getServletPath();
    				// 如果是静态文件，则不更新SESSION
    				if (Servlets.isStaticFile(uri)){
    					return;
    				}
//    				// 如果是视图文件，则不更新SESSION
//    				if (StringUtils.startsWith(uri, Global.getConfig("web.view.prefix"))
//    						&& StringUtils.endsWith(uri, Global.getConfig("web.view.suffix"))){
//    					return;
//    				}
    				// 手动控制不更新SESSION
    				String updateSession = request.getParameter("updateSession");
    				if (updateSession != null && "0".equals(updateSession)){
    					return;
    				}
    			}
    		}
    		session.setAttribute("LAST_TIME", session.getLastAccessTime());
	    	super.touch(key);

		}catch (InvalidSessionException e) {
			// 获取不到SESSION不抛出异常
		}
    }

	@Override
	protected void create(Session session) {
		super.create(session);
	}

	public String getHost(SessionKey key) {
    	try{
    		return super.getHost(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public Collection<Object> getAttributeKeys(SessionKey key) {
    	try{
    		return super.getAttributeKeys(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public Object getAttribute(SessionKey sessionKey, Object attributeKey) {
    	try{
    		return super.getAttribute(sessionKey, attributeKey);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) {
    	try{
    		super.setAttribute(sessionKey, attributeKey, value);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }

    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) {
    	try{
    		return super.removeAttribute(sessionKey, attributeKey);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public void stop(SessionKey key) {
    	try{
    		super.stop(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }

    public void checkValid(SessionKey key) {
    	try{
    		super.checkValid(key);
		}catch (InvalidSessionException e) {
			// 获取不到SESSION不抛出异常
		}
    }

    @Override
    protected Session doCreateSession(SessionContext context) {
    	try{
            System.out.println("doCreateSession===");
    		return super.doCreateSession(context);
		}catch (IllegalStateException e) {
			return null;
		}
    }

	@Override
	protected Session newSessionInstance(SessionContext context) {
		SimpleSession session =  new SimpleSession(context.getHost());
		session.setId(IdGen.uuid());
		return session;
	}

    private  Session newSessionInstance( ) {
        SimpleSession session =  new SimpleSession( );
        session.setId(IdGen.uuid());
        return session;
    }
    @Override
    public Session start(SessionContext context) {
    	try{
            System.out.println("start===");
    		return super.start(context);
		}catch (NullPointerException e) {
			SimpleSession session = new SimpleSession();
			session.setId(0);
			return session;
		}
    }

	@Override
	protected void onExpiration(Session session, ExpiredSessionException ese,
                                SessionKey key) {
 		super.onExpiration(session, ese, key);
	}
	@Override
	protected void onStop(Session session, SessionKey key) {
		super.onStop(session, key);
	}

    @Override
    public Session getSession(SessionKey key) throws SessionException {
        Session session = null;
	    try{
            System.out.println(
                    "getSession====="+((key != null &&  null != key.getSessionId()) ? "null" : key.getSessionId() ));
            session = super.getSession(key);
        }catch (IllegalArgumentException e){
            System.out.println(key+e.getMessage());
            e.printStackTrace();
            session = newSessionInstance();
        }
        return session;
    }
}