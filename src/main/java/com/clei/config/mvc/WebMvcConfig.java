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
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Bean(value = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver mr = new CommonsMultipartResolver();
        mr.setMaxUploadSize(8 * 1024 * 1024);
        mr.setDefaultEncoding("UTF-8");
        return mr;
    }
}