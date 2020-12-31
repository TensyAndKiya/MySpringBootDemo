package com.clei.util;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * RocketMQ Producer
 *
 * @author KIyA
 * @date 2020-12-30
 */
@Component
public class RocketMQProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${mq.topic:SecondTopic}")
    private String topic;

    /**
     * 同步发送
     *
     * @param msg
     */
    public SendResult sendSyncMsg(String msg) {
        return rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(msg).build());
    }

    /**
     * 发送事务消息
     *
     * @param msg
     * @param obj
     */
    public TransactionSendResult sendTransactionMsg(Message msg, Object obj) {
        return rocketMQTemplate.sendMessageInTransaction(topic, msg, obj);
    }
}
