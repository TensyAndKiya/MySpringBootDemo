package com.clei.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "firstFilter",urlPatterns = {"/*"})
public class MyFilter implements Filter, Ordered {

    /**
     * boot 使用filter 使用servlet和listener也是类似的操作
     * 方式1 filter上使用 @WebFilter注解
     *     启动类上使用 @ServletComponentScan注解
     * 方式2 使用配置类 搞一个FilterRegistrationBean
     */


    private Logger logger= LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if(uri.contains("/static/")){
            // 静态资源相关的不记时间
            logger.info("URI:{}",request.getRequestURI());
            long startTime = System.currentTimeMillis();
            filterChain.doFilter(servletRequest,servletResponse);
            logger.info("耗时:{}",(System.currentTimeMillis() - startTime));
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
