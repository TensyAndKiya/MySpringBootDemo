package com.clei.mapper.security;

import com.clei.entity.security.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> selectByUserId(Integer id);
}
