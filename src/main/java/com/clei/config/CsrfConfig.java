package com.clei.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class CsrfConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        // 忽略druid下面的 完美解决了问题。。不用搞什么过滤器之类的。。
        http.authorizeRequests().and().csrf().ignoringAntMatchers("/druid/**");
    }
}
