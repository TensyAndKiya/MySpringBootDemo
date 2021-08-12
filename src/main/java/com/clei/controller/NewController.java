package com.clei.controller;

import com.clei.entity.Dog;
import com.clei.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * 动态添加的controller
 *
 * @author KIyA
 * @date 2021-08-09
 */
public class NewController {

    @Autowired
    private DogService dogService;

    /**
     * 测试方法
     *
     * @return
     */
    @RequestMapping("/test/new/test")
    @ResponseBody
    public Collection<Dog> test() {
        return dogService.getAllAnonymous();
    }
}
