package com.clei;

import com.clei.listener.ApplicationCloseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * 应用启动类
 *
 * @author KIyA
 */
@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
// 扫描filter等
@ServletComponentScan
// 启用swagger2
@EnableSwagger2
// 使得使用注解@ConfigurationProperties的类不用再声明为Component
@ConfigurationPropertiesScan
public class ServerApplication {

    private static Logger logger = LoggerFactory.getLogger(ServerApplication.class);

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

    /**
     * Tasks expected to run during startup should be executed by
     * CommandLineRunner and ApplicationRunner components instead of
     * using Spring component lifecycle callbacks such as @PostConstruct
     *
     * @param ctx
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner1(ApplicationContext ctx) {
        return args -> {
            logger.info("Let's inspect the beans provided by Spring Boot :");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String name : beanNames) {
                logger.info(name);
            }
        };
    }

    @Bean
    public ApplicationRunner applicationRunner1(ApplicationContext ctx) {
        return args -> {
            logger.info("applicationName : {}", ctx.getDisplayName());
            logger.info("applicationRunner1");
        };
    }

    @Bean
    public ApplicationRunner applicationRunner2(ApplicationContext ctx) {
        return args -> {
            logger.info("applicationName : {}", ctx.getDisplayName());
            logger.info("applicationRunner2");
        };
    }

    @Bean
    public ApplicationCloseListener applicationCloseListener() {
        return new ApplicationCloseListener();
    }

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 8888;
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
