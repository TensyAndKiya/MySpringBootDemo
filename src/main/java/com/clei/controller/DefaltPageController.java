package com.clei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaltPageController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/dog")
    public String dog(){
        return "dog";
    }

    @RequestMapping("/tempOrder")
    public String tempOrder(){
        return "tempOrder";
    }
}
