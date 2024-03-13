package com.paofeng.chat;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.paofeng.chat.config.RabbitConfig;
import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.SendMessage;
import com.paofeng.chat.service.WebSocketService;
import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.domain.MQMessage;
import com.paofeng.common.core.domain.R;
import com.paofeng.system.api.RemoteUserService;
import com.paofeng.system.api.domain.UserRelation;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RabbitExchangeConsumer {

    @Resource
    private RemoteUserService remoteUserService;


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

        WebSocketService.sendMessage(chatMessage.getTargetId(), SendMessage.getResult(chatMessage));

    }

    @RabbitHandler
    @RabbitListener(queues = "${rabbitmq.chatTopic}")
    public void processChat(String message) {
        System.out.println("---------------------");
        System.out.println("mq收到消息：" + message);
        try {
            MQMessage<HashMap<String, Object>> m = JSON.parseObject(message, new TypeReference<MQMessage<HashMap<String,
                    Object>>>() {
            });
            Map<String, Object> data = m.getData();
            if (data.containsKey("currentRiderId") && data.containsKey("shopUserId")) {
                ChatMessage<List<UserRelation>> chatMessage = new ChatMessage<>();
                chatMessage.setType(ChatMessage.TYPE_CHAT_WINDOW);

                Long shopUserId = Long.valueOf(data.get("shopUserId").toString());
                Long currentRiderId = Long.valueOf(data.get("currentRiderId").toString());
                // 添加好友关系
                WebSocketService.setUserFriends(shopUserId, currentRiderId);

                R<List<UserRelation>> relationInfoRes =
                        remoteUserService.getRelationInfo(new Long[]{shopUserId, currentRiderId},
                                SecurityConstants.INNER);
                if (R.isSuccess(relationInfoRes)) {
                    List<UserRelation> UserRelationList = relationInfoRes.getData();
                    chatMessage.setTargetId(shopUserId);
                    chatMessage.setData(UserRelationList.stream().filter(e -> e.getUserId().equals(currentRiderId)).collect(Collectors.toList()));
                    WebSocketService.sendCheckHandler(chatMessage);

                    chatMessage.setTargetId(currentRiderId);
                    chatMessage.setData(UserRelationList.stream().filter(e -> e.getUserId().equals(shopUserId)).collect(Collectors.toList()));
                    WebSocketService.sendCheckHandler(chatMessage);
                }
            }

        } catch (Exception e) {
            // 处理异常 防止服务循环报错
            e.printStackTrace();
        }

    }

}
