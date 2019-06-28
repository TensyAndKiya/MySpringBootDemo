package com.clei.controller;

import com.clei.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.clei.service.DogService;

import java.util.Collection;

@Controller
@RequestMapping("/dog")
public class DogController {
	
	@Autowired
	DogService dogService;

	@Value("${application.hello:default value}")
	private String hello;
	
	@RequestMapping(value="getOne",method=RequestMethod.GET)
	@ResponseBody
	public Dog getById(@RequestParam("id") Integer id){
		return dogService.getById(id);
	}

	@RequestMapping(value="getAll",method=RequestMethod.GET)
	@ResponseBody
	public Collection<Dog> getALl(){
		return dogService.getAll();
	}

	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Integer add(Dog dog){
		return dogService.add(dog);
	}

}
