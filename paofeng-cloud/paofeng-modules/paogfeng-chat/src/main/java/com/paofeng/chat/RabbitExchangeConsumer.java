package com.paofeng.chat;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.paofeng.chat.config.RabbitConfig;
import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.SendMessage;
import com.paofeng.chat.service.WebSocketService;
import com.paofeng.common.core.domain.MQMessage;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class RabbitExchangeConsumer {

    @Bean
    public Queue queue() {
        return new Queue(RabbitConfig.topic);
    }

    @Resource
    private WebSocketService webSocketService;

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
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setType(ChatMessage.TYPE_CHAT_WINDOW);

                Long shopUserId = Long.valueOf(data.get("shopUserId").toString());
                Long currentRiderId = Long.valueOf(data.get("currentRiderId").toString());
                chatMessage.setTargetId(shopUserId);
                chatMessage.setContent(currentRiderId.toString());
                webSocketService.sendCheckHandler(chatMessage);

                chatMessage.setTargetId(currentRiderId);
                chatMessage.setContent(shopUserId.toString());
                webSocketService.sendCheckHandler(chatMessage);
            }

        } catch (Exception e) {
            // 处理异常 防止服务循环报错
            e.printStackTrace();
        }

    }

}
