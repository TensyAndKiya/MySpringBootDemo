package com.clei.config.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务测试
 *
 * @author KIyA
 */
@Component
public class ScheduleTask {

    private final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    private int i1 = 0;
    private int i2 = 0;
    private int i3 = 0;

    private int j1 = 0;
    private int j2 = 0;
    private int j3 = 0;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 使用@Async注解后线程池会使用 异步任务设置的线程池
     *
     * @throws InterruptedException
     */
    @Scheduled(fixedDelay = 10 * 1000)
    public void tenSeconds() throws InterruptedException {
        // 定时任务异常处理测试
        boolean isOdd = 1 == (1 & System.currentTimeMillis());
        if (isOdd) {
            throw new RuntimeException("竟然是奇数啊");
        }
        logger.info(LocalDateTime.now().format(dtf) + " fixDelay tenSeconds " + (i1++));
        TimeUnit.SECONDS.sleep(10);
    }

    @Scheduled(fixedDelay = 30 * 1000)
    public void halfMinute() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixDelay halfMinute " + (i2++));
        TimeUnit.SECONDS.sleep(10);
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void oneMinute() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixDelay oneMinute " + (i3++));
        TimeUnit.SECONDS.sleep(10);
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void tenSeconds2() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixRate tenSeconds " + (j1++));
        TimeUnit.SECONDS.sleep(10);
    }

    @Scheduled(fixedRate = 30 * 1000)
    public void halfMinute2() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixRate halfMinute " + (j2++));
        TimeUnit.SECONDS.sleep(10);
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void oneMinute2() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixRate oneMinute " + (j3++));
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 每天 18点9分1秒执行
     */
    @Scheduled(cron = " 1 9 18 * * ?")
    public void cron() {
        for (int i = 0; i < 10; i++) {
            logger.info("cron test");
        }
    }
}
