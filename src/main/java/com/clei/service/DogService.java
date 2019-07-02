package com.clei.service;

import com.clei.entity.Dog;
import com.clei.mapper.DogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class DogService {
	@Autowired
	DogMapper dogMapper;

	public Dog getById(Integer id){
		if(id == 1){
			throw new RuntimeException("哈哈哈，想不到吧！！！");
		}
		return dogMapper.getById(id);
	}

	public Collection<Dog> getAll(){
		return  dogMapper.getAll();
	}

	public Integer add(Dog dog){
		return  dogMapper.add(dog);
	}

    public List<Map<String, Object>> testSelect(Map<String, Object> paramMap) {
		return dogMapper.testSelect(paramMap);
    }
}
