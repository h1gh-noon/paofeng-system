package com.paofeng.order.service;

import com.paofeng.order.domain.Order;
import com.paofeng.order.domain.OrderVo;

import java.util.List;

/**
 * 订单Service接口
 *
 * @author paofeng
 * @date 2024-03-05
 */
public interface IOrderService {
    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    public OrderVo selectOrderByOrderId(String orderId);

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单集合
     */
    public List<OrderVo> selectOrderList(OrderVo order);

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 骑手接单
     *
     * @param orderId 订单id
     * @return 结果
     */
    public int takeOrder(String orderId);

    /**
     * 骑手拿到商品
     *
     * @param orderId 订单id
     * @return 结果
     */
    public int takeOrderGoods(String orderId);

    /**
     * 骑手送达
     *
     * @param orderId 订单id
     * @return 结果
     */
    public int successOrder(String orderId);

    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @return 结果
     */
    public int cancelOrder(String orderId);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(String[] orderIds);

    /**
     * 删除订单信息
     *
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(String orderId);
}
