package com.clei.repository;

import com.clei.entity.elasticsearch.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 订单数据操作类
 *
 * @author KIyA
 * @date 2020-10-28
 */
@Repository
public interface OrderRepository extends ElasticsearchRepository<Order, Long> {

}
