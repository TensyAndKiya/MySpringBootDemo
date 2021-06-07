package com.clei.config.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * 应用关闭之前做数据备份
 *
 * @author KIyA
 * @date 2021-06-07
 */
@Configuration
public class DataBackupConfig {

    private final static Logger logger = LoggerFactory.getLogger(DataBackupConfig.class);

    /**
     * 应用关闭之前做数据备份
     */
    @PreDestroy
    public void dataBackup() {
        logger.info("数据备份 开始");
        try {
            TimeUnit.SECONDS.sleep(20);
            System.out.println("数据备份 结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 记录日志相关的线程也结束掉了 所以看不到log日志
        logger.info("数据备份 结束");
    }
}
