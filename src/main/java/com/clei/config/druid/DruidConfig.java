package com.clei.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// @Configuration
public class DruidConfig {

    // 管理后台的servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> params = new HashMap<>();
        params.put("loginUserName","druid");
        params.put("loginPassword","blackliuli");
        // 运行访问的ip
        params.put("allow","");
        // 禁止访问的ip
        params.put("deny","127.0.0.2");
        bean.setInitParameters(params);
        return bean;
    }
    // web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> params = new HashMap<>();
        params.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(params);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}
