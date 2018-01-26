package com.houdask.site.auth.shiro.session;

import com.houdask.site.common.redis.base.BaseRedisDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
@Repository("shiroSessionDAO")
public class RedisSessionDAO extends AbstractSessionDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String sessionPrefix = "shiroSession_";
    private static final long TIMEOUT = 1800;


    @Autowired
    private BaseRedisDao baseRedisDao;


    private String getKey(String originalKey) {
        return sessionPrefix + originalKey;
    }

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        logger.info("===createSession:{}", sessionId.toString());
//        redisTemplate.opsForValue().set(getKey(sessionId.toString()), session, redisConfig.getSessionTime(), TimeUnit.MINUTES);
        baseRedisDao.set(getKey(sessionId.toString()),session,TIMEOUT );
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.info("===readSession:{}", sessionId.toString());
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = null;
        if (session == null) {
//            session = (Session) redisTemplate.opsForValue().get(getKey(sessionId.toString()));
            session = (Session) baseRedisDao.get(getKey(sessionId.toString()));
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    public void update(Session session) {
        if(session == null || session.getId() == null){
            logger.info("===updateSession:{}", "session == null || session.getId() == null");
            return ;
        }
        logger.info("===updateSession:{}", session.getId().toString());
        String key = getKey(session.getId().toString());
//        redisTemplate.opsForValue().set(key, session, redisConfig.getSessionTime(), TimeUnit.MINUTES);
        baseRedisDao.set(key ,session,TIMEOUT );
    }

    // 删除session
    @Override
    public void delete(Session session) {
        logger.info("===delSession:{}", session.getId());
        baseRedisDao.remove(getKey(session.getId().toString()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Collection<Session> result = new ArrayList<>();
        List<String> keys = baseRedisDao.getByRegular(getKey("*"));
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            Session session =  (Session) baseRedisDao.get(getKey(key));
            logger.info("===getActiveSessions session "/* + session.getAttribute(Contacts.SESSION_SUBJECT)*/);
            result.add(session);
        }
        return result;
    }
}
