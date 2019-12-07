package com.clei.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TestTask {

    Logger logger = LoggerFactory.getLogger(TestTask.class);

    /**
     * 每天 18点9分1秒执行
     */
    @Scheduled(cron = " 1 9 18 * * ?")
    public void test(){
        for (int i = 0; i < 10; i++) {
            logger.info("test test");
            System.out.println("Hello World !");
        }
    }
}
