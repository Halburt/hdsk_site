package com.houdask.site.auth.shiro.session;

import com.houdask.site.common.auth.base.Principal;
import com.houdask.site.auth.shiro.util.Servlets;
import com.houdask.site.common.redis.base.BaseRedisDao;
import com.houdask.site.common.utils.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
//@Repository("shiroSessionDAO")
public class RedisSessionDAO extends AbstractSessionDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private   String sessionPrefix = "shiroSession_";
    private   String All_SESSION = "All_SESSION_";
    private static final long TIMEOUT = 1800;


    @Autowired
    private SessionIdGenerator sessionIdGenerator;


    @Autowired
    private BaseRedisDao baseRedisDao;


    private    String getKey(String originalKey) {
        return sessionPrefix + originalKey;
    }
    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        baseRedisDao.set(getKey(sessionId.toString()), ObjectUtils.serialize( session),TIMEOUT );
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        SimpleSession session = null;
        HttpServletRequest request = Servlets.getRequest();
        if (request != null){
            String uri = request.getServletPath();
            // 如果是静态文件，则不获取SESSION
            if (Servlets.isStaticFile(uri)){
                return null;
            }
            session = (SimpleSession) request.getAttribute("session_"+sessionId);
        }
        if (session != null){
            return session;
        }

        // 先从缓存中获取session，如果没有再去数据库中获取

        if (session == null) {
            byte[] bytes = (byte[]) baseRedisDao.get(getKey(sessionId.toString()));
            if(bytes != null){
                session = (SimpleSession) ObjectUtils.unserialize(bytes);
            }
        }
		if (request != null && session != null){
          request.setAttribute("session_"+sessionId, session);
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    public void update(Session session) {
        if(session == null || session.getId() == null){
            return ;
        }
        String key = getKey(session.getId().toString());
        baseRedisDao.set(key , ObjectUtils.serialize( session),TIMEOUT );
        // 获取登录者编号
        String principalId = (String) session.getAttribute(Principal.Principal_SESSION_KEY);
        if(principalId != null){
            baseRedisDao.addMap(All_SESSION, session.getId().toString(),
                    principalId + "|" + session.getTimeout() + "|" + session.getLastAccessTime().getTime());
        }
    }

    // 删除session
    @Override
    public void delete(Session session) {
        baseRedisDao.remove(getKey(session.getId().toString()));
        baseRedisDao.removeMapField(All_SESSION, session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Collection<Session> result = new ArrayList<>();
        List<String> keys = baseRedisDao.getByRegular(getKey("*"));
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            byte[] bytes = (byte[]) baseRedisDao.get(getKey(key));
            if(bytes != null){
                SimpleSession session = (SimpleSession) ObjectUtils.unserialize(bytes);
                result.add(session);
            }
        }
        return result;
    }


    public String getSessionPrefix() {
        return sessionPrefix;
    }

    public void setSessionPrefix(String sessionPrefix) {
        this.sessionPrefix = sessionPrefix;
    }
}
