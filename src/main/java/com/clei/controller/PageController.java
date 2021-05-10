package com.clei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转 controller
 *
 * @author KIyA
 */
@Controller
@RequestMapping("/page/view")
public class PageController {

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

    @GetMapping("/{page}")
    public String page(@PathVariable("page") String page) {
        return page;
    }
}
