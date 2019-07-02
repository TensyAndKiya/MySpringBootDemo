package com.clei.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.clei.entity.Dog;

@Mapper
public interface DogMapper {

	Dog getById(Integer id);

	Collection<Dog> getAll();

	Integer add(Dog dog);

    List<Map<String, Object>> testSelect(Map<String, Object> paramMap);
}
