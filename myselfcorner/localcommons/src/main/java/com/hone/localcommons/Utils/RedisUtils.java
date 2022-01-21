package com.hone.localcommons.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;
    public static final int TOKEN_EXPIRES_SECOND = 1800;
    public void setValue(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }
    public void setValue(String key, Object val, int time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, val, time, unit);
    }
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}