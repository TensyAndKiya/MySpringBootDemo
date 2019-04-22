package com.clei.service;

import com.clei.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    public Collection<Map<String,Object>> getByPage(String startDate, String endDate, int page, int size) {
        return null;
    }

    public int selectCount(Map<String,Object> map){
        return orderMapper.selectCount(map);
    }

    public List<Map<String, Object>> selectPage(Map<String, Object> map) {
        return orderMapper.selectPage(map);
    }
}
