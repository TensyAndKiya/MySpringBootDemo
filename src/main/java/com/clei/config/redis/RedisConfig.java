package com.clei.config.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;

/**
 * redis 配置类
 *
 * @author KIyA
 * @date 2020-09-14
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${redis.expireTime:5}")
    private Long expireTime;

    private Logger logger= LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){

        logger.info("cacheManager");

        // 自定义jackson序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        // 这里竟然返回了一个RedisCacheConfiguration对象，直接调用entryTtl不行，简直坑爹啊
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(expireTime));

        CacheManager cacheManager =  RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .build();

        return cacheManager;

    }

    @Bean
    public KeyGenerator keyGenerator(){
        return (obj,method,args) -> {
            StringBuilder sb = new StringBuilder();

            sb.append("C_");

            sb.append(obj.getClass().getCanonicalName());

            sb.append("_M_");

            sb.append(method.getName());

            sb.append("_ARGS");

            sb.append(Arrays.toString(args));

            String key = sb.toString();

            logger.info("key : " + key);

            return key;
        };
    }

    /**
     * SpringBoot 2.X 的 redis缓存并没有使用redistemplate
     * @param <T>
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));

        return redisTemplate;
    }

}
