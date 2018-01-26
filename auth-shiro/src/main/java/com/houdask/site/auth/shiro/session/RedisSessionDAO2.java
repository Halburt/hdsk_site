/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.houdask.site.auth.shiro.session;

import com.alibaba.fastjson.JSONObject;
import com.houdask.site.auth.shiro.token.Principal;
import com.houdask.site.auth.shiro.util.Servlets;
import com.houdask.site.common.redis.base.BaseRedisDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;

/**
 * 自定义授权会话管理类
 */
//@Component
public class RedisSessionDAO2 extends AbstractSessionDAO /*implements SessionDAO */{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BaseRedisDao baseRedisDao;

	private String sessionKeyPrefix = "shiro_session_";

    /**
     * session 数据放在2个缓存里
     *
     * shiro_session_    所有session精简信息集合 value： principalId + "|" + session.getTimeout() + "|" + session.getLastAccessTime().getTime());
     * shiro_session_+sessionId   某个具体session缓存  value： session
     *
     * @param session
     * @throws UnknownSessionException
     */
	@Override
	public void update(Session session) throws UnknownSessionException {
		System.out.println("RedisSessionDAO2  update>> ");
		if (session == null || session.getId() == null) {  

            return;
        }
		try {
		    try {
                Principal  p = (Principal) SecurityUtils.getSubject().getPrincipal();
                System.out.println("RedisSessionDAO2  update>> " + JSONObject.toJSONString(p ) +p.getSessionId()) ;
                System.out.println("RedisSessionDAO2  update>> " + session.getId().toString());
            }catch (Exception e){
                System.out.println("RedisSessionDAO2  update>> " + e.getMessage());
            }


			// 获取登录者编号
			String principalId = (String) session.getAttribute(Principal.Principal_SESSION_KEY);
            baseRedisDao.addMap(sessionKeyPrefix, session.getId().toString(),
                        principalId + "|" + session.getTimeout() + "|" + session.getLastAccessTime().getTime());
            //TODO session序列化待处理
            baseRedisDao.set(sessionKeyPrefix + session.getId().toString(), session, (long)(session.getTimeout() / 1000));

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	@Override
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}
		try {
            baseRedisDao.removeMapField(sessionKeyPrefix, session.getId().toString());
            baseRedisDao.remove(sessionKeyPrefix + session.getId().toString());
			logger.debug("delete {} ", session.getId());
		} catch (Exception e) {
			logger.error("delete {} ", session.getId(), e);
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return baseRedisDao.getByRegular(sessionKeyPrefix+"*");
	}


	@Override
	protected Serializable doCreate(Session session) {
		HttpServletRequest request = Servlets.getRequest();
		if (request != null){
			String uri = request.getServletPath();
			// 如果是静态文件，则不创建SESSION
			if (Servlets.isStaticFile(uri)){
		        return null;
			}
		}
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.update(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {

		Session s = null;
		HttpServletRequest request = Servlets.getRequest();
		if (request != null){
			String uri = request.getServletPath();
			// 如果是静态文件，则不获取SESSION
			if (Servlets.isStaticFile(uri)){
				return null;
			}
			s = (Session)request.getAttribute("session_"+sessionId);
		}
		if (s != null){
			return s;
		}

		Session session = null;
		try {
            session = (Session) baseRedisDao.get(sessionKeyPrefix + sessionId.toString());
			logger.debug("doReadSession {} {}", sessionId, request != null ? request.getRequestURI() : "");
		} catch (Exception e) {
			logger.error("doReadSession {} {}", sessionId, request != null ? request.getRequestURI() : "", e);
		}
		if (request != null && session != null){
			request.setAttribute("session_"+sessionId, session);
		}
		return session;
	}
	
	@Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
    	try{
        	return super.readSession(sessionId);
    	}catch (UnknownSessionException e) {
			return null;
		}
    }


}
