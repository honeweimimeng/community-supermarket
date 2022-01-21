package com.hone.localcommons.Client;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Component
public class MQUtils {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;
    public void PushForMQQue(String header,String msg){
        RedisConnection redisConnection= RedisConnectionUtils.getConnection(redisConnectionFactory);
        try {
            redisConnection.lPush(header.getBytes(),msg.getBytes());
        }finally {
            RedisConnectionUtils.releaseConnection(redisConnection,redisConnectionFactory);
        }
    }
    public void PopForMQQue(String header, Consumer<String> res){
        CompletableFuture.runAsync(()->{
            RedisConnection redisConnection= RedisConnectionUtils.getConnection(redisConnectionFactory);
            try {
                while(true){
                    byte[] byte_redis=new byte[0];
                    try {
                        byte_redis=redisConnection.bRPopLPush(0,header.getBytes(),header.getBytes());
                        res.accept(new String(byte_redis));
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        redisConnection.lRem(header.getBytes(),1,byte_redis);
                    }
                }
            }finally {

            }
        });
    }
}