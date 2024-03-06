package com.paofeng.order.service.impl;

import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.domain.R;
import com.paofeng.common.core.exception.CheckedException;
import com.paofeng.common.core.exception.base.BaseException;
import com.paofeng.common.core.utils.DateUtils;
import com.paofeng.common.core.utils.uuid.UUID;
import com.paofeng.common.security.utils.SecurityUtils;
import com.paofeng.order.domain.Order;
import com.paofeng.order.domain.OrderVo;
import com.paofeng.order.mapper.OrderMapper;
import com.paofeng.order.service.IOrderService;
import com.paofeng.system.api.RemoteShopService;
import com.paofeng.system.api.domain.SysShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单Service业务层处理
 *
 * @author paofeng
 * @date 2024-03-05
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Resource
    private RemoteShopService remoteShopService;

    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public OrderVo selectOrderByOrderId(String orderId) {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<OrderVo> selectOrderList(OrderVo order) {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int insertOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString(true));
        order.setCreateTime(DateUtils.getNowDate());

        R<SysShop> shopInfo = remoteShopService.getShopInfoByUserId(SecurityUtils.getUserId(),
                SecurityConstants.INNER);
        if (R.FAIL == shopInfo.getCode()) {
            throw new BaseException("请联系管理员");
        }
        SysShop shop = shopInfo.getData();
        if (!SysShop.STATUS_ENABLE.equals(shop.getStatus())) {
            throw new CheckedException("店铺暂时不可用");
        }

        order.setPickupAddress(shop.getAddress());
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    @Transactional
    @Override
    public int takeOrder(String orderId) {
        OrderVo orderVo = selectOrderByOrderId(orderId);
        if (!Order.TYPE_PUBLISH.equals(orderVo.getStatus())) {
            // 校验状态 只有已发布的才能接单
            throw new CheckedException("该订单已被获取");
        }

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCurrentRider(SecurityUtils.getUserId());
        order.setStatus(Order.TYPE_DELIVERING);
        return updateOrder(order);
    }

    @Transactional
    @Override
    public int successOrder(String orderId) {
        OrderVo orderVo = selectOrderByOrderId(orderId);
        if (!SecurityUtils.getUserId().equals(orderVo.getCurrentRider())) {
            // 校验权限
            throw new CheckedException("401");
        }

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCurrentRider(SecurityUtils.getUserId());
        order.setStatus(Order.TYPE_DELIVERING);
        return updateOrder(order);
    }

    @Transactional
    @Override
    public int cancelOrder(String orderId) {
        OrderVo orderVo = selectOrderByOrderId(orderId);
        Order order = new Order();
        order.setOrderId(orderId);
        Long userId = SecurityUtils.getUserId();
        // 根据userId判断是谁取消的订单
        if (userId.equals(orderVo.getCurrentRider())) {
            // 骑手取消
            order.setStatus(Order.TYPE_CANCEL_RIDER);
            return updateOrder(order);
        }
        if (userId.equals(orderVo.getCreatId())) {
            // 商家取消
            order.setStatus(Order.TYPE_CANCEL_SHOP);
            return updateOrder(order);
        }

        return 0;
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderIds(String[] orderIds) {
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单信息
     *
     * @param orderId 订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderId(String orderId) {
        return orderMapper.deleteOrderByOrderId(orderId);
    }
}
