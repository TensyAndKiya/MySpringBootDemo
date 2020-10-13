package com.clei.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author KIyA
 * @date 2020-10-13
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置key value 永不过期
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置key value 并设置一个过期时间
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置 key value 前提：如果key不存在的话
     *
     * @param key
     * @param value
     * @return 操作是否成功
     */
    public Boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 设置 key value 并设置一个过期时间 前提：如果key不存在的话
     *
     * @param key
     * @param value
     * @return 操作是否成功
     */
    public Boolean setIfAbsent(String key, Object value, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    /**
     * 设置 key value 前提：如果key存在的话
     *
     * @param key
     * @param value
     * @return 操作是否成功
     */
    public Boolean setIfPresent(String key, Object value) {
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    /**
     * 设置 key value 并设置一个过期时间 前提：如果key存在的话
     *
     * @param key
     * @param value
     * @return 操作是否成功
     */
    public Boolean setIfPresent(String key, Object value, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout, timeUnit);
    }

    /**
     * 返回key对应的value
     *
     * @param key
     * @return key -> value
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置新值并返回旧值
     *
     * @param key
     * @param value
     * @return 旧值
     */
    public Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 设置key 在一段时间后过期
     *
     * @param key
     * @return 操作是否成功
     */
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 设置key 在某个时刻过期
     *
     * @param key
     * @return 操作是否成功
     */
    public Boolean expire(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 删除key
     *
     * @param key
     * @return 操作是否成功
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     * @return 操作成功数
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 获取key还有多少秒过期
     *
     * @param key
     * @return key剩余过期秒数
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 获取key还有多久过期，使用指定时间格式
     *
     * @param key
     * @param timeUnit
     * @return key剩余过期时间
     */
    public long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }
}
