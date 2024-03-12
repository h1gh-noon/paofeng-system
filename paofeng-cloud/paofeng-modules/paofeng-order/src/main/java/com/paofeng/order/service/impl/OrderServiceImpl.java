package com.paofeng.order.service.impl;

import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.domain.MQMessage;
import com.paofeng.common.core.domain.R;
import com.paofeng.common.core.exception.CheckedException;
import com.paofeng.common.core.exception.auth.NotPermissionException;
import com.paofeng.common.core.exception.base.BaseException;
import com.paofeng.common.core.utils.DateUtils;
import com.paofeng.common.core.utils.uuid.UUID;
import com.paofeng.common.security.utils.SecurityUtils;
import com.paofeng.order.domain.Order;
import com.paofeng.order.domain.OrderVo;
import com.paofeng.order.mapper.OrderMapper;
import com.paofeng.order.service.IOrderService;
import com.paofeng.order.service.RabbitMQService;
import com.paofeng.system.api.RemoteShopService;
import com.paofeng.system.api.domain.SysShop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单Service业务层处理
 *
 * @author paofeng
 * @date 2024-03-05
 */
@Service
public class OrderServiceImpl implements IOrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RemoteShopService remoteShopService;

    @Resource
    private RabbitMQService rabbitMQService;

    @Value("${rabbitmq.chatTopic}")
    public String chatTopic;


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
        order.setCreatId(SecurityUtils.getUserId());
        order.setCreateTime(DateUtils.getNowDate());

        R<SysShop> shopInfo = remoteShopService.getShopInfoByUserId(order.getCreatId(),
                SecurityConstants.INNER);
        if (R.FAIL == shopInfo.getCode()) {
            throw new BaseException("请联系管理员");
        }
        SysShop shop = shopInfo.getData();
        if (shop == null || !SysShop.STATUS_ENABLE.equals(shop.getStatus())) {
            throw new CheckedException("店铺暂时不可用");
        }
        order.setShopName(shop.getShopName());
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
        order.setTakeTime(DateUtils.getTime());
        order.setCurrentRider(SecurityUtils.getUserId());
        order.setStatus(Order.TYPE_DELIVERING_TAKING);

        int n = updateOrder(order);
        if (n > 0) {
            // 通过mq传递消息
            Map<String, Object> map = new HashMap<>();
            map.put("currentRiderId", order.getCurrentRider());
            map.put("shopUserId", orderVo.getCreatId());
            MQMessage<Map<String, Object>> mqMessage = new MQMessage<>();
            mqMessage.setType("1");
            mqMessage.setData(map);
            rabbitMQService.sendMessage(chatTopic, mqMessage);
        }
        return n;
    }

    @Transactional
    @Override
    public int takeOrderGoods(String orderId) {
        OrderVo orderVo = selectOrderByOrderId(orderId);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setPickupTime(DateUtils.getTime());
        order.setCurrentRider(SecurityUtils.getUserId());
        if (!orderVo.getCurrentRider().equals(order.getCurrentRider())) {
            // 校验只有骑手本人可以修改状态
            throw new NotPermissionException("401");
        }
        if (!Order.TYPE_DELIVERING_TAKING.equals(orderVo.getStatus())) {
            // 校验状态 只有已发布的才能接单
            throw new CheckedException("该订单已取货");
        }

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
        if (!Order.TYPE_DELIVERING.equals(orderVo.getStatus())) {
            // 校验订单状态
            throw new CheckedException("订单异常");
        }

        Order order = new Order();
        order.setOrderId(orderId);
        order.setSuccessTime(DateUtils.getTime());
        order.setCurrentRider(SecurityUtils.getUserId());
        order.setStatus(Order.TYPE_SUCCESS);
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
            if (orderVo.getStatus().equals(Order.TYPE_DELIVERING_TAKING)) {
                // 如果 订单还没取货 那么订单改为已发布状态
                order.setStatus(Order.TYPE_PUBLISH);
            } else {
                // 已经取货 状态改为骑手取消
                order.setStatus(Order.TYPE_CANCEL_RIDER);
            }
        } else {
            // 商家取消
            if (orderVo.getStatus().equals(Order.TYPE_DELIVERING_TAKING)) {
                // 如果 订单还没取货 那么订单改为已发布状态
                order.setStatus(Order.TYPE_PUBLISH);
            } else {
                // 已经取货 状态改为商家取消
                order.setStatus(Order.TYPE_CANCEL_SHOP);
            }
        }

        return updateOrder(order);
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
