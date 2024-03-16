package com.paofeng.chat;

import com.alibaba.fastjson2.JSON;
import com.paofeng.chat.config.RabbitConfig;
import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.service.BaseWebsocketService;
import com.paofeng.chat.service.impl.ChatMedalService;
import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.domain.Order;
import com.paofeng.common.core.domain.R;
import com.paofeng.common.core.utils.DateUtils;
import com.paofeng.system.api.RemoteUserService;
import com.paofeng.system.api.domain.UserRelation;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RabbitExchangeConsumer {

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private ChatMedalService chatMedalService;


    @Bean
    public Queue queue() {
        return new Queue(RabbitConfig.topic);
    }

    @RabbitHandler
    @RabbitListener(queues = "#{queue.name}")
    public void process(String message) throws Exception {
        System.out.println("---------------------");
        System.out.println("mq收到消息：" + message);
        ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);

        BaseWebsocketService.sendMessage(chatMessage.getTargetId(), ChatMessage.getResult(chatMessage));

    }

    @RabbitHandler
    @RabbitListener(queues = "${rabbitmq.chatTopic}")
    public void processOrderChat(String message) {
        System.out.println("---------------------");
        System.out.println("mq收到消息：" + message);
        try {
            Order order = JSON.parseObject(message, Order.class);
            if (order.getCurrentRider() != null && order.getCreatId() != null) {
                Long shopUserId = order.getCreatId();
                Long currentRiderId = order.getCurrentRider();
                ChatMessage<Order> cm = new ChatMessage<>();
                cm.setType(ChatMessage.TYPE_ORDER);
                cm.setData(order);

                // 骑手接单
                if (Order.TYPE_DELIVERING_TAKING.equals(order.getStatus())) {
                    // 订单已被骑手接单 给商家发消息
                    cm.setTargetId(shopUserId);
                    cm.setCreateTime(DateUtils.parseDate(order.getTakeTime()));

                    // 给骑手商家添加好友关系
                    ChatMessage<List<UserRelation>> chatMessage = new ChatMessage<>();
                    chatMessage.setType(ChatMessage.TYPE_CHAT_WINDOW);
                    chatMedalService.setUserFriends(shopUserId, currentRiderId);
                    // 获取对方用户信息进行推送
                    R<List<UserRelation>> relationInfoRes =
                            remoteUserService.getRelationInfo(new Long[]{shopUserId, currentRiderId},
                                    SecurityConstants.INNER);
                    if (R.isSuccess(relationInfoRes)) {
                        // 创建聊天
                        List<UserRelation> UserRelationList = relationInfoRes.getData();
                        chatMessage.setTargetId(shopUserId);
                        chatMessage.setData(UserRelationList.stream().filter(e -> e.getUserId().equals(currentRiderId)).collect(Collectors.toList()));
                        chatMedalService.sendCheckHandler(chatMessage);

                        chatMessage.setTargetId(currentRiderId);
                        chatMessage.setData(UserRelationList.stream().filter(e -> e.getUserId().equals(shopUserId)).collect(Collectors.toList()));
                        chatMedalService.sendCheckHandler(chatMessage);
                    }
                } else if (Order.TYPE_CANCEL_SHOP.equals(order.getStatus())) {
                    // 商家取消 给骑手发消息
                    cm.setTargetId(currentRiderId);
                    cm.setCreateTime(DateUtils.parseDate(order.getSuccessTime()));
                } else if (Order.TYPE_DELIVERING.equals(order.getStatus())) {
                    // 骑手配送中
                    cm.setTargetId(shopUserId);
                    cm.setCreateTime(DateUtils.parseDate(order.getPickupTime()));
                } else {
                    // 骑手取消 成功送达
                    cm.setTargetId(shopUserId);
                    cm.setCreateTime(DateUtils.parseDate(order.getSuccessTime()));
                }

                chatMedalService.sendCheckHandler(cm);
            }

        } catch (Exception e) {
            // 处理异常 防止服务循环报错
            e.printStackTrace();
        }

    }

}
