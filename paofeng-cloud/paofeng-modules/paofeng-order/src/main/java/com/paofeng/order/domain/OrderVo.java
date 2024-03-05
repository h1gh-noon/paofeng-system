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
public class OrderVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "id")
    private String orderId;

    /**
     * 创建者
     */
    @Excel(name = "创建者id")
    private Long creatId;

    /**
     * 创建者
     */
    @Excel(name = "创建者username")
    private String creatName;

    /**
     * 当前骑手
     */
    @Excel(name = "当前骑手id")
    private Long currentRider;

    /**
     * 当前骑手
     */
    @Excel(name = "当前骑手")
    private String currentRiderName;

    /**
     * 取货地址
     */
    @Excel(name = "取货地址")
    private String pickupAddress;

    /**
     * 送货地址 包含联系方式
     */
    @Excel(name = "送货地址 包含联系方式")
    private String destination;

    /**
     * 状态 1待发布 2已发布 3商家取消 4骑手取消
     */
    @Excel(name = "状态 1待发布 2已发布 3商家取消 4骑手取消")
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
