package com.paofeng.chat.service.impl;

import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.service.RabbitMQService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    public String exchange;

    @Override
    public void sendMessage(String routing, String msg) {
        rabbitTemplate.convertAndSend(exchange, routing, msg);
    }

    @Override
    public void sendMessage(String routing, ChatMessage msg) {
        rabbitTemplate.convertAndSend(exchange, routing, msg);
    }
}
