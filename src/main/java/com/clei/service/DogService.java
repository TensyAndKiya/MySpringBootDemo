package com.clei.service;

import com.clei.entity.Dog;
import com.clei.mapper.DogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

@CacheConfig(cacheNames = "dog")
@Service
public class DogService {

    @Autowired
    DogMapper dogMapper;

    private Logger logger= LoggerFactory.getLogger(DogService.class);

    public Dog getById(Integer id) {
        if (id == 1) {
            throw new RuntimeException("哈哈哈，想不到吧！！！");
        }
        return dogMapper.getById(id);
    }

    // 只有yueyaye这个角色才能访问的方法
    // 竟然必须要有ROLE_这个前缀。。。厉害了
    @Secured({"ROLE_超级管理员"})
    public Collection<Dog> getAll() {
        return dogMapper.getAll();
    }

    public Integer add(Dog dog) {
        return dogMapper.add(dog);
    }

    public List<Map<String, Object>> testSelect(Map<String, Object> paramMap) {
        return dogMapper.testSelect(paramMap);
    }


    @Cacheable(keyGenerator = "keyGenerator")
    public Dog getDog(Integer id) {

        logger.info("getDog id : " + id);

        Dog d = new Dog(id,"阿三","黑色",new Random().nextInt(Integer.MAX_VALUE));

        logger.info("getDog result : " + d);

        return d;
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public List<Dog> getDogList() {

        logger.info("getDogList");

        Dog d1 = new Dog(1,"阿三","黑色",new Random().nextInt(Integer.MAX_VALUE));
        Dog d2 = new Dog(2,"阿四","白色",new Random().nextInt(Integer.MAX_VALUE));
        Dog d3 = new Dog(3,"阿五","红色",new Random().nextInt(Integer.MAX_VALUE));

        List<Dog> list = new ArrayList<>(3);

        list.add(d1);
        list.add(d2);
        list.add(d3);

        logger.info("getDogList result : " + list.toString());

        return list;
    }

}
