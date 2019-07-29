package com.clei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultPageController {

    Logger logger = LoggerFactory.getLogger(DefaultPageController.class);

    /*
    使用 mvcConfig的viewController来控制
    @RequestMapping("/index")
    public String index(){
        return "index";
    }*/

    /*@RequestMapping("/error")
    @ResponseBody
    public String error(){
        return "发生错误，请联系管理员";
    }*/

    @RequestMapping("/dog")
    public String dog(){
        return "dog";
    }

    @RequestMapping("/file")
    public String file(){
        return "file";
    }

    @RequestMapping("/chat")
    public String chat(){
        return "chat";
    }

    @RequestMapping("/tempOrder")
    public String tempOrder(){
        return "tempOrder";
    }

    @RequestMapping("/showRoles")
    @ResponseBody
    public String showRoles(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("isAuthenticated :{}",authentication.isAuthenticated());
        logger.info("name :{}",authentication.getName());
        logger.info("authorities :{}",authentication.getAuthorities());
        logger.info("details :{}",authentication.getDetails());
        logger.info("credentials :{}",authentication.getCredentials());
        logger.info("principal :{}",authentication.getPrincipal());
        return "see log";
    }

}
