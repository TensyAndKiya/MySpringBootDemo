package com.clei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultPageController {

    @RequestMapping("/index")
    public String index(){
        return "index";
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
}
