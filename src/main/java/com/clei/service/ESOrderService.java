package com.clei.service;

import com.clei.entity.elasticsearch.Order;
import com.clei.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ESOrderService implements ESService {

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 创建索引
     *
     * @return
     */
    @Override
    public boolean createIndex() {
        return template.createIndex(Order.class);
    }

    /**
     * 批量插入
     *
     * @param list
     */
    public void saveAll(List<Order> list) {
        orderRepository.saveAll(list);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Order> getAll() {
        Iterable<Order> iterable = orderRepository.findAll();
        List<Order> list = new ArrayList<>();
        iterable.forEach(i -> list.add(i));
        return list;
    }
}
