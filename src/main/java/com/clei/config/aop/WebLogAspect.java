package com.clei.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * web切面
 *
 * @author KIyA
 */
@Aspect
@Component
public class WebLogAspect {

    /**
     * 忽略的controller
     */
    private final static Set<String> IGNORE_CONTROLLER = new HashSet() {
        {
            add("PageController");
        }
    };

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Order(1)
    @Pointcut("execution(public * com.clei.controller..*.*(..))")
    public void webLog() {
    }

    @Order(5)
    @Pointcut("execution(public * com.clei.service.*.select*(..))")
    public void webLog2() {
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object object = null;
        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String cName = className.substring(className.lastIndexOf(".") + 1);
            if (IGNORE_CONTROLLER.contains(cName)) {
                return joinPoint.proceed();
            }

            String methodName = joinPoint.getSignature().getName();
            logger.info("around 前");
            logger.info("CLASS_METHOD : " + className + "." + methodName);
            logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
            object = joinPoint.proceed();
            logger.info("around 后");
        } catch (Throwable t) {
            logger.error("around 错误！！！", t);
        }
        logger.info("around returning,result:{}", object);
        return object;
    }

    @Before("webLog2()")
    public void doBeforeRead(JoinPoint joinPoint) {
        logger.info("before read");
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("webLog2()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("after");
    }

    @After("webLog2()")
    public void doAfterRead(JoinPoint joinPoint) {
        logger.info("after read");
    }


    /**
     * 因为之前把接收的object的类型写成Objects了。。。所以死活执行不了。。
     * 瓜皮滴可以哦！！！
     *
     * @param object
     */
    @AfterReturning(pointcut = "webLog2()", returning = "object")
    public void doAfterReturning(Object object) {
        logger.info("after returning,result:{}", object);
    }


    /**
     * afterThrowing只能看看这个异常，并不能对异常进行完全处理
     * 建议还是用around吧。。before after throwing return的结果都能处理。。。
     *
     * @param e
     */
    @AfterThrowing(pointcut = "webLog2()", throwing = "e")
    public void doAfterThrowing(Throwable e) {
        logger.error("after throwing!!!", e);
    }


}
