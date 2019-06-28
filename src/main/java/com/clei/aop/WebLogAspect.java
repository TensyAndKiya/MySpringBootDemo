package com.clei.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebLogAspect {

	private Logger logger=LoggerFactory.getLogger(WebLogAspect.class);
	
	@Order(1)
	@Pointcut("execution(public * com.clei.service..*.get*(..))")
	public void webLog() {}
	
	@Order(5)
	@Pointcut("execution(public * com.clei.service.*.select*(..))")
	public void webLog2() {}

	@Around("webLog()")
	public Object around(ProceedingJoinPoint joinPoint){
		Object object = null;
		try{
			logger.info("around 前");
			object = joinPoint.proceed();
			logger.info("around 后");
		}catch (Throwable t){
			logger.error("aop around 错误！！！",t);
		}
		return object;
	}
	
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


	/**
	 * 因为之前把接收的object的类型写成Objects了。。。所以死活执行不了。。
	 * 瓜皮滴可以哦！！！
	 * @param object
	 */
	@AfterReturning(pointcut = "webLog()",returning = "object")
	public void doAfterReturning(Object object){
		logger.info("after returning,result:{}",object);
	}


	/**
	 * afterThrowing只能看看这个异常，并不能对异常进行完全处理
	 * 建议还是用around吧。。before after throwing return的结果都能处理。。。
	 * @param e
	 */
	@AfterThrowing(pointcut = "webLog()",throwing = "e")
	public void doAfterThrowing(Throwable e){
		logger.error("after throwing!!!",e);
	}


}
