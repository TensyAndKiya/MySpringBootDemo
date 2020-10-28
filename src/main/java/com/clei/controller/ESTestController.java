package com.clei.controller;

import com.clei.entity.elasticsearch.Order;
import com.clei.service.ESOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * elasticsearch操作测试 controller
 *
 * @author KIyA
 * @date 2020-10-28
 */
@RestController
@RequestMapping("/es")
public class ESTestController {

    @Autowired
    private ESOrderService esOrderService;

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 初始化
     *
     * @return
     */
    @RequestMapping("/init")
    public String init() {
        esOrderService.createIndex();

        List<Order> orderList = getOrderList();

        esOrderService.saveAll(orderList);

        return "success";
    }

    /**
     * 初始化
     *
     * @return
     */
    @RequestMapping("/getAll")
    public List<Order> getAll() {
        List<Order> list = esOrderService.getAll();
        return list;
    }

    /**
     * 创建一些订单
     *
     * @return
     */
    private List<Order> getOrderList() {
        Order o1 = new Order();
        o1.setId(1L);
        o1.setOrderName("orderName1");
        o1.setProductId(11L);
        o1.setProductName("蓝色玻璃杯");
        o1.setSellerId(22L);
        o1.setSellerName("蓝色玻璃杯商店");
        o1.setBuyerId(33L);
        o1.setBuyerName("老贼");
        o1.setBuyNum(4);
        o1.setUnitPrice(3D);
        o1.setTotalPrice(o1.getBuyNum() * o1.getUnitPrice());
        o1.setDeductionPrice(3.33D);
        o1.setPayPrice(new BigDecimal(o1.getTotalPrice()).subtract(new BigDecimal(o1.getDeductionPrice())).doubleValue());
        o1.setCreateTime(System.currentTimeMillis() - 333 * 444);
        o1.setPayTime(System.currentTimeMillis());
        o1.setRemark("买来装水的");

        Order o2 = new Order();
        o2.setId(2L);
        o2.setOrderName("orderName2");
        o2.setProductId(123L);
        o2.setProductName("康师傅老坛酸菜面");
        o2.setSellerId(456L);
        o2.setSellerName("康师傅专卖店");
        o2.setBuyerId(789L);
        o2.setBuyerName("饿虎");
        o2.setBuyNum(20);
        o2.setUnitPrice(3.5D);
        o2.setTotalPrice(o2.getBuyNum() * o2.getUnitPrice());
        o2.setDeductionPrice(12D);
        o2.setPayPrice(new BigDecimal(o2.getTotalPrice()).subtract(new BigDecimal(o2.getDeductionPrice())).doubleValue());
        o2.setCreateTime(System.currentTimeMillis() - 123 * 456);
        o2.setPayTime(System.currentTimeMillis());
        o2.setRemark("买来吃的");

        Order o3 = new Order();
        o3.setId(3L);
        o3.setOrderName("orderName3");
        o3.setProductId(1122L);
        o3.setProductName("未开刃的长剑");
        o3.setSellerId(3344L);
        o3.setSellerName("血腥武器店");
        o3.setBuyerId(5566L);
        o3.setBuyerName("刀客");
        o3.setBuyNum(2);
        o3.setUnitPrice(6998D);
        o3.setTotalPrice(o3.getBuyNum() * o3.getUnitPrice());
        o3.setDeductionPrice(1333D);
        o3.setPayPrice(new BigDecimal(o3.getTotalPrice()).subtract(new BigDecimal(o3.getDeductionPrice())).doubleValue());
        o3.setCreateTime(System.currentTimeMillis() - 1111 * 333);
        o3.setPayTime(System.currentTimeMillis());
        o3.setRemark("十步杀一人");

        List<Order> list = new ArrayList<>(3);
        list.add(o1);
        list.add(o2);
        list.add(o3);

        return list;
    }
}
