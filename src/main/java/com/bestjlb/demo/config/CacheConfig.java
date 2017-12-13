package com.bestjlb.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yydx811 on 2017/10/26.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    private Environment env;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(env.getProperty("demo.redis.host", "10.10.10.10"));
        factory.setPort(env.getProperty("demo.redis.port", Integer.class, 6379));
        factory.setDatabase(1);
        factory.setUsePool(true);

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(200);
        config.setMaxTotal(1000);
        factory.setPoolConfig(config);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setHashKeySerializer(redisTemplate.getStringSerializer());
        return redisTemplate;
    }

    @Bean
    CacheManager cacheManager(RedisTemplate redisTemplate) {
        Map<String, Long> expireMap = new HashMap<>();
        expireMap.put("Demo", 20L);
        expireMap.put("DemoList", 30L);

        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setExpires(expireMap);
        cacheManager.setDefaultExpiration(30 * 60L);
        return cacheManager;
    }
}
