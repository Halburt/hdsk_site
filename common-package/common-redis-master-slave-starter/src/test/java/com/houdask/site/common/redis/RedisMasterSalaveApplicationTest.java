package com.houdask.site.common.redis;

import com.houdask.site.common.redis.base.BaseRedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisMasterSalaveApplicationTest {
    //Spring注入一个操作字符串的StringRedisTemplate
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BaseRedisDao dao;
    public static String s  ="";
    @Test
    public void doTest() {
        for (int i = 1; i < 10000; i++) {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       test();
                   } catch (Exception e) {
                       if("".equals(s)){
                           e.printStackTrace();
                           s ="1";
                       }
                       System.out.println("哎呀错误了===");
                   }
               }
           }).start();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
         System.out.println("nowTime:"+nowTime);
        dao.set("nowTime",nowTime);
         System.out.println("get："+dao. get("nowTime"));
    }

    public void set(String key,String value){
        ValueOperations<String,Object> valueOperations =  redisTemplate.opsForValue();
        boolean hasKey =  redisTemplate.hasKey(key);
        if(hasKey) {
            System.out.println("This key has set");
        }else {
            valueOperations.set(key,value);
        }

    }
    public String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }
}