package com.clei;

import com.clei.listener.ApplicationCloseListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
// 扫描filter等
@ServletComponentScan
// 启用swagger2
@EnableSwagger2
public class ServerApplication {

    public static void main(String[] args) {
        // SpringApplication.run(ServerApplication.class, args);
        SpringApplication application = new SpringApplication(ServerApplication.class);
        // application.setBannerMode(Banner.Mode.OFF); 不用banner
        application.run(args);
    }

    public static void test() {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        for (int i = 0; i < 10; i++) {
            System.out.println(pe.encode("" + i));
        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot :");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String name : beanNames) {
                System.out.println(name);
            }
        };
    }

    @Bean
    public ApplicationCloseListener applicationCloseListener() {
        return new ApplicationCloseListener();
    }

    /**
     * 要用的tomcat才行
     *
     * @return
     */
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(applicationCloseListener());
        return tomcat;
    }
}
