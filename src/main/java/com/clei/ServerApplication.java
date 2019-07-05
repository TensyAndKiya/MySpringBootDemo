package com.clei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
public class ServerApplication {
    public static void main(String[] args) {
        // SpringApplication.run(ServerApplication.class, args);
        SpringApplication application = new SpringApplication(ServerApplication.class);
        // application.setBannerMode(Banner.Mode.OFF); 不用banner
        application.run(args);
    }
    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot :");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String name : beanNames){
                System.out.println(name);
            }
        };
    }*/
}
