package com.paofeng.common.core.domain;

import com.paofeng.common.core.annotation.Excel;
import com.paofeng.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单对象 order
 *
 * @author paofeng
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    // 待发布
    public static final String TYPE_DRAFT = "1";
    // 已发布
    public static final String TYPE_PUBLISH = "2";
    // 骑手已接单 待取货
    public static final String TYPE_DELIVERING_TAKING = "3";
    // 骑手已接单 配送中
    public static final String TYPE_DELIVERING = "4";
    // 商家取消
    public static final String TYPE_CANCEL_SHOP = "5";
    // 骑手取消
    public static final String TYPE_CANCEL_RIDER = "6";
    // 成功送达结束
    public static final String TYPE_SUCCESS = "9";

    /**
     * id
     */
    @Excel(name = "id")
    @NotNull(groups = {Validation.Update.class})
    private String orderId;

    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private Long creatId;

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
    @Excel(name = "当前骑手")
    private Long currentRider;

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
     * 状态 1待发布 2已发布 3商家取消 4骑手取消
     */
    @Excel(name = "状态 1待发布 2已发布 3骑手已接单 4商家取消 5骑手取消 6配送完成")
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
