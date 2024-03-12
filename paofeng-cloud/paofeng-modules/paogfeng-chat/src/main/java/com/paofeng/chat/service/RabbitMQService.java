package com.paofeng.chat.service;

public interface RabbitMQService {
    void sendMessage(String routing, String msg);
}
