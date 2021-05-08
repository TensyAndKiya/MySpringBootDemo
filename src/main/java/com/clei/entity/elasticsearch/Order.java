package com.clei.entity.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 订单
 *
 * @author KIyA
 * @date 2020-10-28
 */
@Document(indexName = "order_index", type = "_doc", shards = 1, replicas = 0, createIndex = false)
public class Order {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 订单名
     */
    @Field(type = FieldType.Text)
    private String orderName;

    /**
     * 产品id
     */
    @Field(type = FieldType.Long)
    private Long productId;

    /**
     * 产品名称
     */
    @Field(type = FieldType.Keyword)
    private String productName;

    /**
     * 卖家id
     */
    @Field(type = FieldType.Long)
    private Long sellerId;

    /**
     * 卖家名称
     */
    @Field(type = FieldType.Keyword)
    private String sellerName;

    /**
     * 买家id
     */
    @Field(type = FieldType.Long)
    private Long buyerId;

    /**
     * 买家名称
     */
    @Field(type = FieldType.Keyword)
    private String buyerName;

    /**
     * 购买份数
     */
    @Field(type = FieldType.Integer)
    private Integer buyNum;

    /**
     * 产品单价
     */
    @Field(type = FieldType.Double)
    private Double unitPrice;

    /**
     * 订单总价
     */
    @Field(type = FieldType.Double)
    private Double totalPrice;

    /**
     * 优惠抵扣金额
     */
    @Field(type = FieldType.Double)
    private Double deductionPrice;

    /**
     * 订单应付金额
     */
    @Field(type = FieldType.Double)
    private Double payPrice;

    /**
     * 订单创建时间
     */
    @Field(type = FieldType.Long)
    private Long createTime;

    /**
     * 买家付款时间
     */
    @Field(type = FieldType.Long)
    private Long payTime;

    /**
     * 订单备注
     */
    @Field(type = FieldType.Text)
    private String remark;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", buyerId=" + buyerId +
                ", buyerName='" + buyerName + '\'' +
                ", buyNum=" + buyNum +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", deductionPrice=" + deductionPrice +
                ", payPrice=" + payPrice +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDeductionPrice() {
        return deductionPrice;
    }

    public void setDeductionPrice(Double deductionPrice) {
        this.deductionPrice = deductionPrice;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
