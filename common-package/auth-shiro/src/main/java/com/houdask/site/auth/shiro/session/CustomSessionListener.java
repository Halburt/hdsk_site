package com.houdask.site.auth.shiro.session;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
public class CustomSessionListener implements SessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisSessionDAO redisSessionDAO;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        logger.info("onStart:{}", session.getId());
    }
    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        logger.info("onStop:{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("onExpiration:{}", session.getId());
        redisSessionDAO.delete(session);
    }

}

