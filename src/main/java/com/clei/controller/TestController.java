package com.clei.controller;

import com.alibaba.fastjson.JSONObject;
import com.clei.config.runner.ListStrConfig;
import com.clei.constant.Global;
import com.clei.entity.security.User;
import com.clei.service.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private Global global;
    @Autowired
    private ListStrConfig config;
    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    // 普通spring web项目里得Controller使用@Value会有问题，
    // 然而springboot项目里就没问题。。有点意思哦

    @Value("${application.hello}")
    private String str;

    @GetMapping("insertUser")
    @ResponseBody
    public User insertUser(){
        User user = new User();
        user.setLoginName("yueyaye");
        user.setNickname("月澤淵");
        user.setPassword("blackliuli");
        user.setGender(true);
        user.setAge(18);
        user.setEmail("yueyaye@163.com");

        userService.insert(user);

        return user;
    }

    @PostMapping("commonPost")
    public String acceptJson(String json){
        logger.info("json : {}",json);
        JSONObject obj = new JSONObject();
        obj.put("message","");
        obj.put("status","success");
        return obj.toJSONString();
    }

    @GetMapping("test")
    @ResponseBody
    public String test(){
        /*logger.info("This str:{},Global.str:{}",str,global.str);
        logger.info("new Global:{}",new Global().str);*/

        logger.info("{}",config.getStrs());
        return str == null ? global.str : str;
    }

    @RequestMapping("/jsonTest")
    @ResponseBody
    public Map<String,Object> jsonTest(String json){

        logger.info(json);
        JSONObject jsonObject = null;
        if(null != json && !"".equals(json)){
            jsonObject = JSONObject.parseObject(json);
            logger.info("{}",jsonObject.get("name").toString().equals("张三"));
            logger.info("{}",jsonObject.get("age"));
            logger.info("{}",jsonObject.get("weight"));
            logger.info("{}",jsonObject.get("dog"));
        }
        return jsonObject;
    }
}
