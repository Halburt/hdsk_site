package com.houdask.site.common.redis;

import com.houdask.site.common.redis.base.BaseRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class TestRedisController {
    @Autowired
    private BaseRedisDao dao;
    @Resource
    protected RedisTemplate redisTemplate;
    @RequestMapping("/get")
    public String  test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = (String) redisTemplate.opsForValue(). get("nowTime");
        s = sdf.format(new Date());

        System.out.println("nowTime:"+s);
        try {
            redisTemplate.opsForValue().set("nowTime",s);
//            s = (String) redisTemplate.opsForValue(). get("nowTime");
//            System.out.println("get："+ s);
        }catch (Exception e){
e.printStackTrace();
            s += "出错啦";
        }

        return s;
    }

}
