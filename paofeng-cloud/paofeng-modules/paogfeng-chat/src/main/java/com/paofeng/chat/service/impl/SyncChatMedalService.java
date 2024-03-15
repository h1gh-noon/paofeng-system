package com.paofeng.chat.service.impl;

import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.SendMessage;
import com.paofeng.chat.service.BaseWebsocketService;
import com.paofeng.chat.service.IChatMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

@Service(ChatMessage.TYPE_SYNC_CHAT)
public class SyncChatMedalService extends BaseWebsocketService {

    @Resource
    private IChatMessageService chatMessageService;

    @Override
    public void execute(ChatMessage chatMessage, Session session) {
        // 同步聊天记录
        List<SendMessage> chatMessages = chatMessageService.selectChatMessageListByUser(chatMessage);
        try {
            sendMessage(session, SendMessage.getSync(chatMessages));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
