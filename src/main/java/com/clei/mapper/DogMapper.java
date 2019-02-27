package com.clei.mapper;

import java.util.Collection;

import org.apache.ibatis.annotations.Mapper;

import com.clei.entity.Dog;

@Mapper
public interface DogMapper {
	Collection<Dog> selectAll();
}
