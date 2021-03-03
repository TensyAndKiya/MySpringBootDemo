package com.clei.service;

import com.alibaba.fastjson.JSONObject;
import com.clei.bo.ESRange;
import com.clei.entity.elasticsearch.Order;
import com.clei.repository.OrderRepository;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ESOrderService implements ESService {

    private ElasticsearchRestTemplate template;

    private OrderRepository orderRepository;

    /**
     * 构造器注入可省略@Autowired注解
     */
    @Autowired
    public ESOrderService(ElasticsearchRestTemplate template, OrderRepository orderRepository) {
        this.template = template;
        this.orderRepository = orderRepository;
    }

    private static Logger logger = LoggerFactory.getLogger(ESOrderService.class);

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
     * 单个插入/修改
     *
     * @param order
     */
    public void save(Order order) {
        orderRepository.save(order);
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
     * 只更新document的部分字段
     *
     * @param obj 数据源
     * @param id  document id
     */
    private void updatePart(Object obj, String id) {
        String json = JSONObject.toJSONString(obj);
        UpdateRequest updateRequest = new UpdateRequest().doc(json, XContentType.JSON);
        UpdateQuery query = new UpdateQueryBuilder()
                .withClass(obj.getClass())
                .withId(id)
                .withUpdateRequest(updateRequest)
                .build();
        template.update(query);
        // 当索引一个文档，文档先是被存储在内存里面，默认1秒后，会进入文件系统缓存，这样该文档就可以被搜索
        // 使用下面的操作可以使得更新索引数据的时候，要保证被索引的文档能够立即被搜索到
        template.refresh(obj.getClass());
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

    /**
     * 简单分组
     *
     * @return
     */
    public Map<String, Object> simpleGroup() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders.terms("productName_group").field("productName"))
                .withPageable(PageRequest.of(0, 1))
                .build();

        Page<Order> result = orderRepository.search(nativeSearchQuery);

        result.get().forEach(System.out::println);

        logger.info(result.getClass().getName());

        AggregatedPage<Order> aggregatedPage = (AggregatedPage<Order>) result;

        Aggregation aggregation = aggregatedPage.getAggregation("productName_group");

        logger.info(aggregation.getClass().getName());

        ParsedStringTerms terms = (ParsedStringTerms) aggregation;

        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        logger.info("name : " + terms.getName());
        logger.info("type : " + terms.getType());
        buckets.forEach(b -> {
            logger.info(b.getKeyAsString() + " : " + b.getDocCount());
        });

        return terms.getMetaData();
    }

    /**
     * 简单max
     *
     * @return
     */
    public Double max() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                // 啥字段也不查 减少数据量
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes().build())
                .addAggregation(AggregationBuilders.max("payPrice_max").field("payPrice"))
                .withPageable(PageRequest.of(0, 1))
                .build();

        Page<Order> result = orderRepository.search(nativeSearchQuery);

        result.get().forEach(System.out::println);

        logger.info(result.getClass().getName());

        AggregatedPage<Order> aggregatedPage = (AggregatedPage<Order>) result;

        Aggregation aggregation = aggregatedPage.getAggregation("payPrice_max");

        logger.info(aggregation.getClass().getName());

        ParsedMax max = (ParsedMax) aggregation;

        double payPriceMax = max.getValue();

        return payPriceMax;
    }

    /**
     * 复杂分组
     * 找到各个店家下面销量最高的产品及其总收入金额
     *
     * @return
     */
    public List<Map<String, Object>> complexGroup() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders.terms("sellerName_group").field("sellerName")
                        .subAggregation(AggregationBuilders.terms("productName_group").field("productName")
                                .subAggregation(AggregationBuilders.sum("payPrice_sum").field("payPrice"))))
                .withPageable(PageRequest.of(0, 1))
                .build();

        Page<Order> result = orderRepository.search(nativeSearchQuery);

        result.get().forEach(System.out::println);

        logger.info(result.getClass().getName());

        AggregatedPage<Order> aggregatedPage = (AggregatedPage<Order>) result;

        Aggregation aggregation = aggregatedPage.getAggregation("sellerName_group");

        logger.info(aggregation.getClass().getName());

        ParsedStringTerms terms = (ParsedStringTerms) aggregation;

        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        logger.info("name : " + terms.getName());
        logger.info("type : " + terms.getType());

        // 结果集
        List<Map<String, Object>> resultList = new ArrayList<>(3);

        buckets.forEach(b -> {
            Aggregations aggregations = b.getAggregations();
            logger.info(aggregations.getClass().getName());
            aggregations.forEach(a -> {
                logger.info(a.getClass().getName());
                ParsedStringTerms parsedStringTerms = (ParsedStringTerms) a;
                List<? extends Terms.Bucket> buckets1 = parsedStringTerms.getBuckets();

                double max = 0;
                // 找到最大的
                for (Terms.Bucket bb : buckets1) {
                    Aggregation agg = bb.getAggregations().get("payPrice_sum");
                    ParsedSum sum = (ParsedSum) agg;
                    double s = sum.getValue();
                    max = max > s ? max : s;
                }
                final double resultMax = max;
                // 匹配谁是最大的
                for (Terms.Bucket bb : buckets1) {
                    Aggregation agg = bb.getAggregations().get("payPrice_sum");
                    logger.info(agg.getClass().getName());

                    ParsedSum sum = (ParsedSum) agg;
                    double s = sum.getValue();
                    if (s == resultMax) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("sellerName", b.getKey());
                        m.put("productName", bb.getKey());
                        m.put("payPriceSumMax", s);
                        resultList.add(m);
                    }
                }

            });
        });

        return resultList;
    }

    /**
     * 各个价格区间的订单数量
     *
     * @return
     */
    public List<Map<String, Object>> unitPriceRange() {
        String aggName = "unitPriceRange";
        RangeAggregationBuilder unitPriceRange = AggregationBuilders.range(aggName).field("unitPrice");
        List<ESRange> range = getRange();
        // range 加上
        range.forEach(r -> {
            // 左闭右开的range
            if (null == r.getFrom()) {
                unitPriceRange.addUnboundedTo(r.getKey(), r.getTo());
            } else if (null == r.getTo()) {
                unitPriceRange.addUnboundedFrom(r.getKey(), r.getFrom());
            } else {
                unitPriceRange.addRange(r.getKey(), r.getFrom(), r.getTo());
            }
        });
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .addAggregation(unitPriceRange)
                .withPageable(PageRequest.of(0, 1))
                .build();
        // 查询
        Page<Order> result = orderRepository.search(nativeSearchQuery);
        AggregatedPage<Order> aggregatedPage = (AggregatedPage<Order>) result;
        ParsedRange parsedRange = (ParsedRange) aggregatedPage.getAggregation(aggName);
        List<? extends Range.Bucket> buckets = parsedRange.getBuckets();
        // 遍历处理
        List<Map<String, Object>> list = buckets.stream().map(b -> {
            Map<String, Object> m = new HashMap<>(3);
            m.put("from", b.getFrom());
            m.put("to", b.getTo());
            m.put("count", b.getDocCount());
            return m;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 各个购买数量区间的订单数量
     *
     * @return
     */
    public List<Map<String, Object>> buyNumRange() {
        String aggName = "buyNumRange";
        RangeAggregationBuilder buyNumRange = AggregationBuilders.range(aggName).field("buyNum");
        List<ESRange> range = getRange2();
        // range 加上
        range.forEach(r -> {
            // 左闭右开的range
            if (null == r.getFrom()) {
                buyNumRange.addUnboundedTo(r.getKey(), r.getTo());
            } else if (null == r.getTo()) {
                buyNumRange.addUnboundedFrom(r.getKey(), r.getFrom());
            } else {
                buyNumRange.addRange(r.getKey(), r.getFrom(), r.getTo());
            }
        });
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .addAggregation(buyNumRange)
                .withPageable(PageRequest.of(0, 1))
                .build();
        // 查询
        Page<Order> result = orderRepository.search(nativeSearchQuery);
        AggregatedPage<Order> aggregatedPage = (AggregatedPage<Order>) result;
        ParsedRange parsedRange = (ParsedRange) aggregatedPage.getAggregation(aggName);
        List<? extends Range.Bucket> buckets = parsedRange.getBuckets();
        // 遍历处理
        List<Map<String, Object>> list = buckets.stream().map(b -> {
            Map<String, Object> m = new HashMap<>(3);
            m.put("from", b.getFrom());
            m.put("to", b.getTo());
            m.put("count", b.getDocCount());
            return m;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 获得range
     *
     * @return
     */
    private List<ESRange> getRange() {
        List<ESRange> list = new ArrayList<>(3);
        ESRange range1 = new ESRange();
        range1.setFrom(null);
        range1.setTo(10D);
        range1.setKey(range1.getFrom() + "-" + range1.getTo());
        ESRange range2 = new ESRange();
        range2.setFrom(10D);
        range2.setTo(4455.33D);
        range2.setKey(range2.getFrom() + "-" + range2.getTo());
        ESRange range3 = new ESRange();
        range3.setFrom(4455.33D);
        range3.setTo(null);
        range3.setKey(range3.getFrom() + "-" + range3.getTo());
        list.add(range1);
        list.add(range2);
        list.add(range3);
        return list;
    }

    /**
     * 获得range
     *
     * @return
     */
    private List<ESRange> getRange2() {
        List<ESRange> list = new ArrayList<>(3);
        ESRange range1 = new ESRange();
        range1.setFrom(0D);
        range1.setTo(11D);
        range1.setKey(range1.getFrom() + "-" + range1.getTo());
        ESRange range2 = new ESRange();
        range2.setFrom(11D);
        range2.setTo(20D);
        range2.setKey(range2.getFrom() + "-" + range2.getTo());
        ESRange range3 = new ESRange();
        range3.setFrom(20D);
        range3.setTo(null);
        range3.setKey(range3.getFrom() + "-" + range3.getTo());
        list.add(range1);
        list.add(range2);
        list.add(range3);
        return list;
    }
}
