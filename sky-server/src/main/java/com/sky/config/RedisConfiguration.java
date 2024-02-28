package com.sky.config;

import jdk.jpackage.internal.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate (RedisConnectionFactory redisConnectionFactory) {
        Log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        // 设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置redis key的序列化器 插入和查询数据只要使用一个序列化器就不会乱码
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
