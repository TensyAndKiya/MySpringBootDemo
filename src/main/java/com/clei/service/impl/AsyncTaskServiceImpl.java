package com.clei.service.impl;

import com.clei.service.AsyncTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步任务服务类 impl
 *
 * @author KIyA
 */
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    private static Logger logger = LoggerFactory.getLogger(AsyncTaskServiceImpl.class);

    /**
     * 处理测试数据
     */
    @Override
    @Async
    public void handleTestData() {
        try {
            Thread.sleep(5000L);
            logger.info("异步任务执行完毕");
        } catch (Exception e) {
            logger.error("异步任务执行出错", e);
        }
        // 异步任务异常处理测试
        boolean isOdd = 1 == (1 & System.currentTimeMillis());
        if (isOdd) {
            throw new RuntimeException("竟然是奇数啊");
        }
    }
}
