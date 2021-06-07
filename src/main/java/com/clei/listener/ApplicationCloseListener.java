package com.clei.listener;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 应用关闭监听器
 *
 * @author KIyA
 * @date 2021-06-07
 */
public class ApplicationCloseListener implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private volatile Connector connector;
    private final int waitMinute = 10;

    private final static Logger logger = LoggerFactory.getLogger(ApplicationCloseListener.class);

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        logger.info("关闭开始");
        connector.pause();
        Executor executor = connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ThreadPoolExecutor ex = (ThreadPoolExecutor) executor;
                ex.shutdown();
                // 等正在运行的线程一段时间
                boolean terminated = ex.awaitTermination(waitMinute, TimeUnit.SECONDS);
                if (!terminated) {
                    logger.warn("关闭线程池失败");
                }
            } catch (Exception e) {
                logger.error("关闭线程池出错", e);
            }
        }
        logger.info("关闭结束");
    }
}
