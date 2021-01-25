package com.clei.listener.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.clei.entity.Dog;
import com.clei.service.DogService;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * rocketmq消息消费监听
 * 这里本应放在另一个服务，模拟分布式事务的
 *
 * @author KIyA
 * @date 2020-12-30
 */
// @Component
// @RocketMQMessageListener(topic = "${mq.topic:SecondTopic}", consumerGroup = "${mq.consumer.group:ConsumerGroup}")
public class RocketMqMsgListener implements RocketMQListener<String> {

    @Autowired
    private DogService dogService;

    Logger logger = LoggerFactory.getLogger(RocketMqMsgListener.class);

    @Override
    public void onMessage(String msg) {
        try {
            // 业务处理
            JSONObject json = JSONObject.parseObject(msg);
            if ("addDog".equals(json.getString("operation"))) {
                Dog dog = JSONObject.parseObject(json.getString("data"), Dog.class);
                // 这里再加一狗，每次成功加两狗
                dogService.addDogInTransaction(dog);
            }
        } catch (Exception e) {
            logger.info("出错消息内容：" + msg);
            logger.error("消费消息出错", e);

        }
    }
}
