package com.clei.config.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务及定时任务相关配置
 * 启用异步任务和定时任务
 *
 * @author KIyA
 * @date 2021-05-08
 */
@EnableAsync
@EnableScheduling
@Configuration
public class AsyncConfig implements AsyncConfigurer, SchedulingConfigurer {

    private static Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

    /**
     * 异步任务异常处理器
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            logger.info("异步任务执行出错！method : {}, args : {}", method.getDeclaringClass().getCanonicalName() + "." + method.getName(), Arrays.toString(objects), throwable);
        };
    }

    /**
     * 异步任务线程池配置
     *
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(9);
        executor.setKeepAliveSeconds(60);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-executor-");
        // 不在新线程执行任务 在调用者所在线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setRejectedExecutionHandler((r, exe) -> exe.getQueue().add(r));

        // 等所有任务执行完再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池关闭时等待任务的时间，保证最后能关闭，不会一直等待
        executor.setAwaitTerminationSeconds(60);
        // 初始化
        executor.initialize();
        return executor;
    }

    /**
     * 定时任务线程池配置
     *
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        String threadNamePrefix = "schedule-executor-";
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(4);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler((r, exe) -> exe.getQueue().add(r));
        executor.initialize();
        // 异常处理
        executor.setErrorHandler(throwable -> logger.info("异步任务执行出错！", throwable));

        scheduledTaskRegistrar.setTaskScheduler(executor);
    }
}
