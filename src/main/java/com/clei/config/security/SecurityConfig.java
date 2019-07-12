package com.clei.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 忽略druid下面的 完美解决了问题。。不用搞什么过滤器之类的。。
        http.authorizeRequests().and().csrf().ignoringAntMatchers("/druid/**")
            .ignoringAntMatchers("/login/do");
        // 最多允许100个用户同时登陆
        http.sessionManagement().maximumSessions(100).expiredUrl("/login/view");
        // 认证
        http.authorizeRequests()
                // 对静态资源和登陆页面放行
                .antMatchers("/static/**").permitAll()
                .antMatchers("/login/**","/insertUser").permitAll()
                // 其他请求都要经过认证
                .anyRequest().authenticated();
        // 表单登陆 跳转界面
        http.formLogin().loginPage("/login/view")
                .failureUrl("/login/error")
                .loginProcessingUrl("/login/do")
                .defaultSuccessUrl("/index");
        // 登出处理
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login/view")
                .logoutSuccessHandler((request,response,authentication) -> {
                    // 登出成功处理函数
                    logger.info("登出成功！！！");
                    response.sendRedirect("/login/view");
                }).addLogoutHandler((request,response,authentication) -> {
                    // 登出处理函数
                    logger.info("登出！！！");
                }).invalidateHttpSession(true);


        logger.info("配置完毕！！！");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}