package com.houdask.site.auth.shiro.session;

import com.houdask.site.common.redis.base.BaseRedisDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * redis实现共享session
 */
@Component
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    // session 在redis过期时间是30分钟30*60
    private static long expireTime = 1800;

    private static String prefix = "shiro-session:";

//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private BaseRedisDao baseRedisDao;

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:{}", session.getId());
//        redisTemplate.opsForValue().set(prefix + sessionId.toString(), session);
        baseRedisDao.set(prefix + sessionId.toString(),session );
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
          //  session = (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
            session = (Session) baseRedisDao.get(prefix + sessionId.toString());
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        logger.debug("获取session:{}", session.getId());
        String key = prefix + session.getId().toString();
//        if (!redisTemplate.hasKey(key)) {
//            redisTemplate.opsForValue().set(key, session);
//        }
//        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        if(!baseRedisDao.exists(key)){
            baseRedisDao.set(key  ,session );
        }
        baseRedisDao.setExpireTime(key, expireTime);
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        logger.debug("删除session:{}", session.getId());
        super.doDelete(session);
//        redisTemplate.delete(prefix + session.getId().toString());
        baseRedisDao.remove(prefix + session.getId().toString());
    }
}