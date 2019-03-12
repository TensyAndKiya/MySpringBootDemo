package com.clei.service;

import com.clei.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clei.mapper.DogMapper;

import java.util.Collection;

@Service
public class DogService {
	
	@Autowired
	DogMapper dogMapper;

	public Dog getById(Integer id){
		return dogMapper.getById(id);
	}

	public Collection<Dog> getAll(){
		return  dogMapper.getAll();
	}

	public Integer add(Dog dog){
		return  dogMapper.add(dog);
	}
}
