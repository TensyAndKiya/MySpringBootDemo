package com.clei.mapper.security;

import com.clei.entity.security.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByUsername(String name);

    Integer insert(User user);
}
