package com.clei.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    Collection<Map<String,Object>> getByPage();

    int selectCount(Map<String,Object> map);

    List<Map<String, Object>> selectPage(Map<String, Object> map);
}
