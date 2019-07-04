package com.clei.controller;

import com.alibaba.fastjson.JSONObject;
import com.clei.constant.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class DefaultPageController {

    @Autowired
    private Global global;

    private static Logger logger = LoggerFactory.getLogger(DefaultPageController.class);

    // 普通spring web项目里得Controller使用@Value会有问题，
    // 然而springboot项目里就没问题。。有点意思哦

    @Value("${application.hello}")
    private String str;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("test")
    @ResponseBody
    public String test(){
        logger.info("This str:{},Global.str:{}",str,global.str);
        logger.info("new Global:{}",new Global().str);
        return str == null ? global.str : str;
    }

    @RequestMapping("/dog")
    public String dog(){
        return "dog";
    }

    @RequestMapping("/chat")
    public String chat(){
        return "chat";
    }

    @RequestMapping("/tempOrder")
    public String tempOrder(){
        return "tempOrder";
    }

    @RequestMapping("/jsonTest")
    @ResponseBody
    public Map<String,Object> jsonTest(String json){

        System.out.println(json);
        JSONObject jsonObject = null;
        if(null != json && !"".equals(json)){
            jsonObject = JSONObject.parseObject(json);
            System.out.println(jsonObject.get("name").toString().equals("张三"));
            System.out.println(jsonObject.get("age"));
            System.out.println(jsonObject.get("weight"));
            System.out.println(jsonObject.get("dog"));
        }
        return jsonObject;
    }
}
