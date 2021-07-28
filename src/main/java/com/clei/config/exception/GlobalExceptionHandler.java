package com.clei.config.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author KIyA
 * @date 2021-04-07
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("MethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        if (CollectionUtils.isEmpty(bindingResult.getAllErrors())) {
            return "参数错误";
        }
        return bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                // 竟然有乱序的情况，加个排序保证相同请求参数肯定是相同的响应结果
                .sorted()
                .collect(Collectors.joining(";"));
    }

    /**
     * 处理其它异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public String handleException(Exception e) {
        logger.error("Exception", e);
        String msg = e.getMessage();
        return StringUtils.isBlank(msg) ? "系统错误" : msg;
    }
}
