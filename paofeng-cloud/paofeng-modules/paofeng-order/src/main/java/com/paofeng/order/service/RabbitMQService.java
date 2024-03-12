package com.paofeng.order.service;

public interface RabbitMQService {
    <T> void sendMessage (String routing, T msg);
}
