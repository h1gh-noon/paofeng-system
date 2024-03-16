package com.paofeng.chat.service.impl;

import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.ChatMessageVo;
import com.paofeng.chat.service.BaseWebsocketService;
import com.paofeng.chat.service.IChatMessageService;
import com.paofeng.common.core.utils.bean.BeanUtils;
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
        ChatMessageVo chatMessageVo = new ChatMessageVo();
        BeanUtils.copyBeanProp(chatMessageVo, chatMessage);
        List<ChatMessage> chatMessages = chatMessageService.selectChatMessageListByUser(chatMessageVo);
        try {
            sendMessage(session, ChatMessage.getSync(chatMessages));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
