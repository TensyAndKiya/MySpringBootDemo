package com.clei.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * boot 使用filter 使用servlet和listener也是类似的操作
 * 方式1 filter上使用 @WebFilter注解
 *     启动类上使用 @ServletComponentScan注解
 * 方式2 使用配置类 搞一个FilterRegistrationBean
 */

@WebFilter(filterName = "firstFilter",urlPatterns = {"/*"})
public class MyFilter implements Filter, Ordered {

    private Logger logger= LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if(uri.contains("/static/")){
            /*HttpSession session = request.getSession();
            Object token = session.getAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME);
            if(null != token){
                logger.info("token : {}",token);
            }
            Enumeration<String> sessionAttrs = session.getAttributeNames();
            while (sessionAttrs.hasMoreElements()){
                String key = sessionAttrs.nextElement();
                logger.info("key:{}   value:{}",key,session.getAttribute(key));
            }
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                logger.info("c key:{}    c value:{}",cookie.getName(),cookie.getValue());
            }*/
            filterChain.doFilter(servletRequest,servletResponse);

        }else{
            logger.info("{} {}",request.getMethod(),request.getRequestURI());
            long startTime = System.currentTimeMillis();
            filterChain.doFilter(servletRequest,servletResponse);
            logger.info("{} {}  耗时:{}",request.getMethod(),request.getRequestURI(),(System.currentTimeMillis() - startTime));
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
