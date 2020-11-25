package com.template.controller;

import com.template.config.RedisUtil;
import com.template.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/9/5 14:22
 * @description TODO
 */
@Slf4j
@RequestMapping("/redis")
@RestController
public class RedisController {

    private static int ExpireTime = 60; //redis中存储的过期时间60s

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "set", method = RequestMethod.GET)
    public boolean redisset(String key, String value) {
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setGuid(String.valueOf(1));
        user.setName("zhangsan");
        user.setAge(String.valueOf(20));
        user.setCreateTime(new Date());

//        return redisUtil.set(key, user, ExpireTime);
        return redisUtil.set(key, value);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Object redisget(String key) {
        return redisUtil.get(key);
    }

    @RequestMapping(value = "expire", method = RequestMethod.GET)
    public boolean expire(String key) {
        return redisUtil.expire(key, ExpireTime);
    }
}
