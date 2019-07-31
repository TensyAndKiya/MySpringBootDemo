package com.clei.config.security;

import com.clei.service.security.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${system.max-session}")
    private int maxSession;

    @Autowired
    private MyUserDetailsService userDetailsService;

    private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 忽略druid下面的 完美解决了问题。。不用搞什么过滤器之类的。。
        http.authorizeRequests().and().csrf().ignoringAntMatchers("/druid/**","/file/upload");
        // 最多允许100个用户同时登陆
        http.sessionManagement().maximumSessions(maxSession).expiredUrl("/login");
        // 认证
        http.authorizeRequests()
                // 对静态资源和测试接口
                .antMatchers("/static/**", "/insertUser").permitAll()
                // 其他请求都要经过认证
                .anyRequest().authenticated();
        // 表单登陆 跳转界面
        http.formLogin()
                // 暂不使用自定义的登录页面
                // .loginPage("/login/view")
                // .loginProcessingUrl("/login/do")
                .failureUrl("/login/error")
                .defaultSuccessUrl("/")
                .permitAll();
        // 登出处理
        http.logout()
                // 暂不用自定义登出url
                // .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .logoutSuccessHandler((request, response, authentication) -> {
                    // 登出成功处理函数
                    // 有了这个logoutSuccessUrl就不管用了
                    logger.info("用户{}登出成功", authentication.getName());
                    response.sendRedirect("/login");
                }).addLogoutHandler((request, response, authentication) -> {
                    // 登出处理函数
                    logger.info("用户{}登出", authentication.getName());
                }).invalidateHttpSession(true);

        logger.info("SecurityConfig 配置完毕");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
