package com.clei.config.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class MyCommandLineRunner2 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // do something
        System.out.println(Arrays.toString(args));
    }

}
