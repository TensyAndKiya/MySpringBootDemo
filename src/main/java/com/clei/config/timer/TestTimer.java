package com.clei.config.timer;

import com.clei.config.filter.MyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 测试Timer
 *
 * @author KIyA
 */
// @Component
public class TestTimer {

    private Logger logger = LoggerFactory.getLogger(MyFilter.class);

    private int i1 = 0;
    private int i2 = 0;
    private int i3 = 0;

    private int j1 = 0;
    private int j2 = 0;
    private int j3 = 0;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedDelay = 10 * 1000)
    public void tenSeconds() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixDelay tenSeconds " + (i1 ++));
        Thread.sleep(10 * 1000);
    }

    @Scheduled(fixedDelay = 30 * 1000)
    public void halfMinute() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixDelay halfMinute " + (i2 ++));
        Thread.sleep(10 * 1000);
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void oneMinute() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixDelay oneMinute " + (i3 ++));
        Thread.sleep(10 * 1000);
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void tenSeconds2() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixRate tenSeconds " + (j1 ++));
        Thread.sleep(10 * 1000);
    }

    @Scheduled(fixedRate = 30 * 1000)
    public void halfMinute2() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixRate halfMinute " + (j2 ++));
        Thread.sleep(10 * 1000);
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void oneMinute2() throws InterruptedException {
        logger.info(LocalDateTime.now().format(dtf) + " fixRate oneMinute " + (j3 ++));
        Thread.sleep(10 * 1000);
    }

}
