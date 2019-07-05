package com.clei.config.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyCommandLineRunner1 implements CommandLineRunner, Ordered {
    @Override
    public void run(String... args) throws Exception {
        // do something
        System.out.println(Arrays.toString(args));
        if(args.length > 2){
            args[2] = "hasaki!";
        }
    }

    @Override
    public int getOrder() {
        // 越小越优先
        return 0;
    }
}
