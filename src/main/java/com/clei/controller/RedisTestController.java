package com.clei.controller;

import com.alibaba.fastjson.JSONObject;
import com.clei.entity.Dog;
import com.clei.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类测试 controller
 *
 * @author KIyA
 * @date 2020-10-13
 */
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 基本的 String get set expire操作
     *
     * @return
     */
    @RequestMapping("/basic")
    public String basic() {
        String key = "stringKey";
        String value = "stringValue";
        long timeout = 10;

        logger.info("key : {}, value : {}", key, value);

        redisUtil.set(key, value);

        Object getValue = redisUtil.get(key);

        logger.info("get value : {}", getValue);

        logger.info("get value class : {}", getValue.getClass().getName());

        logger.info("timeout : {}", timeout);

        Boolean expireResult = redisUtil.expire(key, timeout, TimeUnit.SECONDS);

        logger.info("expireResult : {}", expireResult);

        long getExpire = redisUtil.getExpire(key);

        logger.info("getExpire : {}", getExpire);

        long getUnitExpire = redisUtil.getExpire(key, TimeUnit.MILLISECONDS);

        logger.info("getUnitExpire : {}", getUnitExpire);

        redisUtil.delete(key);

        logger.info("value after delete : {}", redisUtil.get(key));

        return "over";
    }

    /**
     * 复杂的对象的 get set操作
     *
     * @return
     */
    @RequestMapping("/complex")
    public String complex() {
        String key = "listKey";
        List<Dog> value = new ArrayList<>(3);

        Dog d1 = new Dog(1, "阿三", "黑色", new Random().nextInt(Integer.MAX_VALUE));
        Dog d2 = new Dog(2, "阿四", "白色", new Random().nextInt(Integer.MAX_VALUE));
        Dog d3 = new Dog(3, "阿五", "红色", new Random().nextInt(Integer.MAX_VALUE));
        value.add(d1);
        value.add(d2);
        value.add(d3);

        logger.info("key : {}, value : {}", key, value);

        redisUtil.set(key, value);

        Object getValue = redisUtil.get(key);

        logger.info("get value : {}", JSONObject.toJSONString(getValue));

        logger.info("get value class : {}", getValue.getClass().getName());

        return "over";
    }

    /**
     * 复杂的对象的 get set操作
     *
     * @return
     */
    @RequestMapping("/getAndSet")
    public String getAndSet() {
        String key = "getAndSet";
        String value = "hasaki";

        logger.info("key : {}, value1 : {}", key, value);

        Object oleValue1 = redisUtil.getAndSet(key, value);

        logger.info("oleValue1 : {}", oleValue1);

        value = "kisaha";

        logger.info("key : {}, value2 : {}", key, value);

        Object oleValue2 = redisUtil.getAndSet(key, value);

        logger.info("oleValue2 : {}", oleValue2);

        return "over";
    }

    /**
     * setNx 测试
     *
     * @return
     */
    @RequestMapping("/setNx")
    public String setNx() {
        String key = "lockKey";
        String result = "处理中...";
        long value = System.currentTimeMillis();
        // 加锁
        Boolean lockResult = redisUtil.setIfAbsent(key, value, 2, TimeUnit.SECONDS);
        if(lockResult){
            // 业务处理...
            try {
                Thread.sleep(1200L);
                result = "处理成功...";
            } catch (InterruptedException e) {
                logger.error("业务处理出错 ： ", e);
                result = "处理出错...";
            }finally {
                // 解锁
                redisUtil.delete(key);
            }
        }
        logger.info("result : {}", result);
        return result;
    }
}
