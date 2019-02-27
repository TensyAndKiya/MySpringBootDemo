package com.clei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clei.mapper.DogMapper;

@Service
public class TestService {
	
	@Autowired
	DogMapper dogMapper;

	public int doSth(String a,int b) {
		System.out.println("doSth "+a+"  "+b);
		return b;
	}
	
	public void readSth(String a,int b) {
		System.out.println("readSth "+a+"  "+b);
	}
	
	public void readAllDog() {
		System.out.println(dogMapper.selectAll());
	}
}
