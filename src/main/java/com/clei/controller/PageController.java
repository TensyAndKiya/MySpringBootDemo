package com.clei.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.clei.service.TestService;

@Controller
@RequestMapping("/controller")
public class PageController {
	
	@Autowired
	TestService tService;

	@Value("${application.hello:default value}")
	private String hello;
	
	@RequestMapping(value="f1",method=RequestMethod.GET)
	public String p1(Map<String,Object> map){
		System.out.println("what happened");
		map.put("hello", hello);
		tService.doSth("aaaa", 3333);
		tService.readSth("aaaa", 3333);
		tService.readAllDog();
		return "hello";
	}
}
