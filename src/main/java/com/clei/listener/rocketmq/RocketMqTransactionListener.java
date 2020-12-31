package com.clei.listener.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.clei.entity.Dog;
import com.clei.service.DogService;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * rocketmq 事务消息处理
 *
 * @author KIyA
 * @date 2020-12-30
 */
@Component
@RocketMQTransactionListener
public class RocketMqTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private DogService dogService;

    private final static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(RocketMqTransactionListener.class);

    /**
     * 执行本地事务
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String msg = new String((byte[]) message.getPayload(), RemotingHelper.DEFAULT_CHARSET);
            JSONObject json = JSONObject.parseObject(msg);
            if ("addDog".equals(json.getString("operation"))) {
                Dog dog = JSONObject.parseObject(json.getString("data"), Dog.class);
                // 执行出错
                if ("大黄".equals(dog.getName())) {
                    throw new RuntimeException("大黄不行啊");
                }
                dogService.addDogInTransaction(dog);
                // 用于check
                map.put(json.getString("id"), 1);
                return RocketMQLocalTransactionState.COMMIT;
            }
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            logger.error("执行本地事务出错", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        try {
            String msg = new String((byte[]) message.getPayload(), RemotingHelper.DEFAULT_CHARSET);
            JSONObject json = JSONObject.parseObject(msg);
            String id = json.getString("id");
            Integer num = map.get(id);
            if (null == num) {
                return RocketMQLocalTransactionState.UNKNOWN;
            }
        } catch (Exception e) {
            logger.error("检查本地事务出错", e);
        }
        return RocketMQLocalTransactionState.COMMIT;
    }
}
