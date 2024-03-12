package com.paofeng.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.paofeng.order.service.RabbitMQService;
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
    public <T> void sendMessage(String routing, T msg) {
        if (msg instanceof String) {
            rabbitTemplate.convertAndSend(exchange, routing, msg);
        } else {
            rabbitTemplate.convertAndSend(exchange, routing, JSON.toJSONString(msg));
        }
    }
}
