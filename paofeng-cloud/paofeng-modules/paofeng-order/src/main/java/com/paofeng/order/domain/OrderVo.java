package com.paofeng.order.domain;

import com.paofeng.common.core.annotation.Excel;
import com.paofeng.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单对象 order
 *
 * @author paofeng
 */

@EqualsAndHashCode(callSuper = true)
@Data
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

    @Excel(name = "店铺名称")
    private String shopName;

    @Excel(name = "骑手接单时间")
    private String takeTime;

    @Excel(name = "取货时间")
    private String pickupTime;

    @Excel(name = "送达时间")
    private String successTime;

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

    @Excel(name = "顾客姓名")
    private String customerName;

    @Excel(name = "顾客手机号")
    private String customerPhone;

    @Excel(name = "顾客地址")
    private String customerAddress;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

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

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "下单时间")
    private Date createTime;
}
