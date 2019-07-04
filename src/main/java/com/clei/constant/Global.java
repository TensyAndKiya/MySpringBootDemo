package com.clei.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Global {
    // 这个字段不能是static的，否则无法注入
    // 若对象不是通过spring bean获取，也无法注入
    @Value("${application.hello}")
    public String str;
}
