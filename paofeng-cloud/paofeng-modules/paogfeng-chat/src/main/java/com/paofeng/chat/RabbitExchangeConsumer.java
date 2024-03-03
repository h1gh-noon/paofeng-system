package com.paofeng.chat;

import com.alibaba.fastjson2.JSON;
import com.paofeng.chat.config.RabbitConfig;
import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.SendMessage;
import com.paofeng.chat.service.WebSocketService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitExchangeConsumer {

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

}
