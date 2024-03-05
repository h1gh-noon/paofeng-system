package com.paofeng.order.domain;

import com.paofeng.common.core.annotation.Excel;
import com.paofeng.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 订单对象 order
 *
 * @author paofeng
 */
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    // 待发布
    public static final String TYPE_DRAFT = "1";
    // 已发布
    public static final String TYPE_PUBLISH = "2";
    // 骑手已接单 配送中
    public static final String TYPE_DELIVERING = "3";
    // 商家取消
    public static final String TYPE_CANCEL_SHOP = "4";
    // 骑手取消
    public static final String TYPE_CANCEL_RIDER = "5";
    // 成功送达结束
    public static final String TYPE_SUCCESS = "6";

    /**
     * id
     */
    @Excel(name = "id")
    private String orderId;

    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private Long creatId;

    /**
     * 当前骑手
     */
    @Excel(name = "当前骑手")
    private Long currentRider;

    /**
     * 取货地址
     */
    @Excel(name = "取货地址")
    private String pickupAddress;

    /**
     * 送货地址 包含联系方式
     */
    @Excel(name = "送货信息 包含联系方式")
    private String destination;

    /**
     * 状态 1待发布 2已发布 3商家取消 4骑手取消
     */
    @Excel(name = "状态 1待发布 2已发布 3骑手已接单 4商家取消 5骑手取消 6配送完成")
    private String type;

    /**
     * 商品价值
     */
    @Excel(name = "商品价值")
    private BigDecimal productMoney;

    /**
     * 配送费
     */
    @Excel(name = "配送费")
    private BigDecimal deliveryFee;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setCreatId(Long creatId) {
        this.creatId = creatId;
    }

    public Long getCreatId() {
        return creatId;
    }

    public void setCurrentRider(Long currentRider) {
        this.currentRider = currentRider;
    }

    public Long getCurrentRider() {
        return currentRider;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setProductMoney(BigDecimal productMoney) {
        this.productMoney = productMoney;
    }

    public BigDecimal getProductMoney() {
        return productMoney;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", getOrderId())
                .append("creatId", getCreatId())
                .append("currentRider", getCurrentRider())
                .append("pickupAddress", getPickupAddress())
                .append("destination", getDestination())
                .append("type", getType())
                .append("productMoney", getProductMoney())
                .append("deliveryFee", getDeliveryFee())
                .append("createTime", getCreateTime())
                .toString();
    }
}
