package com.clei.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
        // registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico");
    }
}