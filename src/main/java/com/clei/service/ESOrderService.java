package com.clei.service;

import com.alibaba.fastjson.JSONObject;
import com.clei.entity.elasticsearch.Order;
import com.clei.repository.OrderRepository;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
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

@Service
public class ESOrderService implements ESService {

    private ElasticsearchRestTemplate template;

    private OrderRepository orderRepository;

    // 构造器注入可省略@Autowired注解
    // @Autowired
    public ESOrderService(ElasticsearchRestTemplate template, OrderRepository orderRepository) {
        this.template = template;
        this.orderRepository = orderRepository;
    }

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

        System.out.println(result.getClass().getName());

        AggregatedPage<Order> aggregatedPage = (AggregatedPage<Order>) result;

        Aggregation aggregation = aggregatedPage.getAggregation("productName_group");

        System.out.println(aggregation.getClass().getName());

        ParsedStringTerms terms = (ParsedStringTerms) aggregation;

        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        System.out.println("name : " + terms.getName());
        System.out.println("type : " + terms.getType());
        buckets.forEach(b -> {
            System.out.println(b.getKeyAsString() + " : " + b.getDocCount());
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
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("").build())
                .addAggregation(AggregationBuilders.max("payPrice_max").field("payPrice"))
                .withPageable(PageRequest.of(0, 1))
                .build();

        Page<Order> result = orderRepository.search(nativeSearchQuery);

        result.get().forEach(System.out::println);

        System.out.println(result.getClass().getName());

        AggregatedPage<Order> aggregatedPage = (AggregatedPage<Order>) result;

        Aggregation aggregation = aggregatedPage.getAggregation("payPrice_max");

        System.out.println(aggregation.getClass().getName());

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

        System.out.println(result.getClass().getName());

        AggregatedPage<Order> aggregatedPage = (AggregatedPage<Order>) result;

        Aggregation aggregation = aggregatedPage.getAggregation("sellerName_group");

        System.out.println(aggregation.getClass().getName());

        ParsedStringTerms terms = (ParsedStringTerms) aggregation;

        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        System.out.println("name : " + terms.getName());
        System.out.println("type : " + terms.getType());

        // 结果集
        List<Map<String, Object>> resultList = new ArrayList<>(3);

        buckets.forEach(b -> {
            Aggregations aggregations = b.getAggregations();
            System.out.println(aggregations.getClass().getName());
            aggregations.forEach(a -> {
                System.out.println(a.getClass().getName());
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
                    System.out.println(agg.getClass().getName());

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
}
