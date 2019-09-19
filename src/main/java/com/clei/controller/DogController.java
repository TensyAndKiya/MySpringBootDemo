package com.clei.controller;

import com.clei.entity.Dog;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.clei.service.DogService;

import java.util.*;

@Controller
@RequestMapping("/dog")
public class DogController {
	
	@Autowired
	DogService dogService;

	@Value("${application.hello:default value}")
	private String hello;

	@Value("${maven.profiles.active}")
	private String activeProfile;

	private static Logger logger = LoggerFactory.getLogger(DogController.class);
	
	@RequestMapping(value="getOne",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取狗子的详细信息", notes = "没什么需要注意的")
	@ApiImplicitParam(name = "id",value = "狗子的id",dataType = "Integer")
	public Dog getById(@RequestParam(value = "id") Integer id){

		logger.info("Active Profile : {}",activeProfile);

		if(null == id){
			logger.error("你竟然传个空值？？？");
			return null;
		}
		privateMethod(false);
		privateMethod(0);
		privateMethod("1");
		return dogService.getById(id);
	}

	@RequestMapping(value="getAll",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有狗子", notes = "没分页")
	public Collection<Dog> getAll(){
		return dogService.getAll();
	}

	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "传入Dog对象创建狗子", notes = "没什么需要注意的")
	public Integer add(@ApiParam(name = "dog",value = "狗子实体类") Dog dog){
		return dogService.add(dog);
	}

	/**
	 * 该方法用于测试特殊的 参数 Map<String,Object> map Object里有String 有List
	 * 来进行select
	 * @return
	 */
	@RequestMapping(value="test",method=RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> test(){
		logger.info("测试使用logback来记录{}","日志");

		Map<String,Object> paramMap = new HashMap<>();
		List<Map<String,String>> paramList = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		map.put("name","嘿嘿嘿");
		map.put("color","赤");
		paramList.add(map);
		Map<String,String> map2 = new HashMap<>();
		map2.put("name","超级黑");
		map2.put("color","混");
		paramList.add(map2);
		Map<String,String> map3 = new HashMap<>();
		map3.put("name","中黑");
		map3.put("color","白");
		paramList.add(map3);
		Map<String,String> map4 = new HashMap<>();
		map4.put("name","小黑");
		map4.put("color","黑色");
		paramList.add(map4);
		Map<String,String> map5 = new HashMap<>();
		map5.put("name","asdf");
		map5.put("color","asdf");
		paramList.add(map5);
		paramMap.put("list",paramList);
		paramMap.put("age",0);

		List<Map<String, Object>> result = dogService.testSelect(paramMap);
		System.out.println("SIZE:" + result.size());

		return result;
	}

	// 下面的这几个方法只能被controller 里的request方法调用。。
	// 所以不会再被拦截
	// aop 测试
	private void privateMethod(String s){
		if(null == s){

		}else {

		}
	}

	// aop 测试
	private void privateMethod(Integer s){
		if(null == s){

		}else {

		}
	}

	// aop 测试
	void privateMethod(Boolean s){
		if(null == s){

		}else {

		}
	}

}
