package com.clei.config.runner;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "str")
public class ListStrConfig {
    private List<String> strs = new ArrayList<>();
    public List<String> getStrs(){
        return this.strs;
    }
}
