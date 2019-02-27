package com.clei.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebLogAspect {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Order(1)
	@Pointcut("execution(public * com.clei.service.*.do*(..))")
	public void webLog() {}
	
	@Order(5)
	@Pointcut("execution(public * com.clei.service.*.read*(..))")
	public void webLog2() {}
	
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		logger.info("before");
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("webLog2()")
	public void doBeforeRead(JoinPoint joinPoint) {
		logger.info("before read");
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@After("webLog()")
	public void doAfter(JoinPoint joinPoint) {
		logger.info("after");
	}
	
	@After("webLog2()")
	public void doAfterRead(JoinPoint joinPoint) {
		logger.info("after read");
	}
	
	@AfterReturning("webLog()")
	public void doAfterReturning(){
		logger.info("after returning");
	}
}
