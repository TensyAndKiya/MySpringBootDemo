package com.clei.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//下面这个会导致一些自动配置失效   WebMvcAutoConfiguration的自动配置
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//下面这个过时了
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
    }

    /**
     * 跨域设置
     * 方法1 实现WebMvcConfigurer 重写addCorsMappings方法 如下
     * 方法2 Filter中在Response中添加对应header
     * 方法3 对接口或者controller使用@CrossOrigin注解
     * 方法4 返回新的CorsFilter
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH", "TRACE", "CONNECT")
                // 是否允许浏览器发送cookie
                .allowCredentials(true)
                // 预检请求的有效期，单位秒，有效期内不用发出另一条预检请求
                .maxAge(3600)
        ;
    }

    @Bean(value = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver mr = new CommonsMultipartResolver();
        mr.setMaxUploadSize(8 * 1024 * 1024);
        mr.setDefaultEncoding("UTF-8");
        return mr;
    }
}