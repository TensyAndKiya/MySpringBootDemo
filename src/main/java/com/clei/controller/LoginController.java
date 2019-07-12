package com.clei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/login/view")
    public String view(){

        logger.info("login");

        return "login";
    }

    @GetMapping("/login/error")
    @ResponseBody
    public String error(){
        return "登陆出错！！！";
    }

    /*@RequestMapping("/login/do")
    @ResponseBody
    public String doLogin(String username,String password){
        logger.info("username:{}  password:{}",username,password);
        return "登陆成功！！！";
    }*/

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(){
        return "登出";
    }

}
