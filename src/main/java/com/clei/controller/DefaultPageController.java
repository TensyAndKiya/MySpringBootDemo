package com.clei.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class DefaultPageController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("test")
    @ResponseBody
    public String test(){
        return "test success!";
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
