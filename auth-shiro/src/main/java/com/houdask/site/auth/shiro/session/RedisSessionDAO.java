/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.houdask.site.auth.shiro.session;

import com.houdask.site.auth.shiro.util.Servlets;
import com.houdask.site.common.redis.base.BaseRedisDao;
import com.houdask.site.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 自定义授权会话管理类
 */
@Component
public class RedisSessionDAO extends AbstractSessionDAO implements SessionDAO {

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
		if (session == null || session.getId() == null) {  
            return;
        }
		try {
			// 获取登录者编号
			PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			String principalId = pc != null ? pc.getPrimaryPrincipal().toString() : "null";
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
		return getActiveSessions(true);
	}
	
	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @return
	 */
	public Collection<Session> getActiveSessions(boolean includeLeave) {
		return getActiveSessions(includeLeave, null, null);
	}
	
	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @param principal 根据登录者对象获取活动会话
	 * @param filterSession 不为空，则过滤掉（不包含）这个会话。
	 * @return
	 */
	public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession){
		Set<Session> sessions = null;
		try {
			Map<String, String> map = baseRedisDao.getMap(sessionKeyPrefix) ;// jedis.hgetAll(sessionKeyPrefix);

			for (Map.Entry<String, String> e : map.entrySet()){
			    if(null != e.getKey() && !"".equals(e.getKey()) && null != e.getValue() && !"".equals(e.getValue()) )  {
                    String[] ss = e.getValue().split( "|");
					if (ss != null && ss.length == 3){
					    // jedis.exists(sessionKeyPrefix + e.getKey())){
						// Session session = (Session)JedisUtils.toObject(jedis.get(JedisUtils.getBytesKey(sessionKeyPrefix + e.getKey())));
						SimpleSession session = new SimpleSession();
						session.setId(e.getKey());
						session.setAttribute("principalId", ss[0]);
						session.setTimeout(Long.valueOf(ss[1]));
						session.setLastAccessTime(new Date(Long.valueOf(ss[2])));
						try{
							// 验证SESSION
							session.validate();
							boolean isActiveSession = false;
							// 不包括离线并符合最后访问时间小于等于3分钟条件。
							if (includeLeave || DateUtils.pastMinutes(session.getLastAccessTime()) <= 3){
								isActiveSession = true;
							}
							// 符合登陆者条件。
							if (principal != null){
								PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
								if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : StringUtils.EMPTY)){
									isActiveSession = true;
								}
							}
							// 过滤掉的SESSION
							if (filterSession != null && filterSession.getId().equals(session.getId())){
								isActiveSession = false;
							}
							if (isActiveSession){
								sessions.add(session);
							}
							
						}
						// SESSION验证失败
						catch (Exception e2) {
                            baseRedisDao.removeMapField(sessionKeyPrefix, e.getKey() );
						}
					}
					// 存储的SESSION不符合规则
					else{
                        baseRedisDao.removeMapField(sessionKeyPrefix, e.getKey() );
					}
				}
				// 存储的SESSION无Value
				else if (StringUtils.isNotBlank(e.getKey())){
                    baseRedisDao.removeMapField(sessionKeyPrefix, e.getKey() );
				}
			}
			logger.info("getActiveSessions size: {} ", sessions.size());
		} catch (Exception e) {
			logger.error("getActiveSessions", e);
		}
		return sessions;
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
		Jedis jedis = null;
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

	public String getSessionKeyPrefix() {
		return sessionKeyPrefix;
	}

	public void setSessionKeyPrefix(String sessionKeyPrefix) {
		this.sessionKeyPrefix = sessionKeyPrefix;
	}

}
