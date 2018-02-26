package com.houdask.site.common.redis;

import com.houdask.site.common.redis.base.BaseRedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisTest {
    @Autowired
    private BaseRedisDao service;

    @Test
    public void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        System.out.println("nowTime:"+nowTime);
        service.set("nowTime",nowTime);
        System.out.println("get："+service.get("nowTime"));
    }
}