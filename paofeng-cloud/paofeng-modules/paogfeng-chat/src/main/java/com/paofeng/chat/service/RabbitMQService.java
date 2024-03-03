package com.paofeng.chat.service;

import com.paofeng.chat.domain.ChatMessage;

public interface RabbitMQService {

    void sendMessage(String routing, String msg);

    void sendMessage(String routing, ChatMessage msg);
}
