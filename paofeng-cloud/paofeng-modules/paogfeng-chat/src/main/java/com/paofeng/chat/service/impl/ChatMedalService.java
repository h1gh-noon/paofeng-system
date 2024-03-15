package com.paofeng.chat.service.impl;

import com.alibaba.fastjson2.JSON;
import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.SendMessage;
import com.paofeng.chat.service.BaseWebsocketService;
import com.paofeng.chat.service.IChatMessageService;
import com.paofeng.chat.service.RabbitMQService;
import com.paofeng.common.core.web.domain.AjaxResult;
import com.paofeng.common.redis.service.RedisService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;

@Service(ChatMessage.TYPE_CHAT)
public class ChatMedalService extends BaseWebsocketService {

    private static final Logger log = LoggerFactory.getLogger(ChatMedalService.class);

    @Resource
    private IChatMessageService chatMessageService;

    @Resource
    private RedisService redisService;

    @Resource
    private RabbitMQService rabbitMQService;

    @Override
    public void execute(ChatMessage chatMessage, Session session) {
        try {
            // 默认1 私聊
            Long oldId = chatMessage.getId();
            chatMessageService.insertChatMessage(chatMessage);
            log.info("====={}", chatMessage);
            Long targetId = chatMessage.getTargetId();
            // 检查用户关系 检查对方(接收者)是否有发送者好友
            if (checkUserFriends(targetId, getLoginUser(session).getUserid())) {
                sendMessage(session, SendMessage.getReply(oldId, chatMessage));
                sendCheckHandler(chatMessage);
            } else {
                sendMessage(session, AjaxResult.error());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserFriends(Long userId, Long friendId) {
        if (userId == null || friendId == null) {
            return false;
        }
        Set<Long> userFriends = redisService.getCacheSet(getUserFriendsRedisKey(userId));
        return userFriends.contains(friendId);
    }

    public void setUserFriends(Long userId, Long friendId) {
        if (userId == null || friendId == null) {
            return;
        }
        Set<Long> userFriendsSet = redisService.getCacheSet(getUserFriendsRedisKey(userId));
        userFriendsSet.add(friendId);
        redisService.setCacheSet(USER_FRIENDS + ":USER_ID_" + userId, userFriendsSet);

        Set<Long> friendsSet = redisService.getCacheSet(getUserFriendsRedisKey(friendId));
        friendsSet.add(userId);
        redisService.setCacheSet(USER_FRIENDS + ":USER_ID_" + friendId, friendsSet);
    }

    public void sendCheckHandler(ChatMessage chatMessage) throws IOException {
        if (webSocketMap.containsKey(chatMessage.getTargetId())) {
            // 消息接收用户连接在此服务
            sendMessage(chatMessage.getTargetId(), SendMessage.getResult(chatMessage));
        } else {
            // 不在此服务 查询redis
            String userRoutingKey = redisService.getCacheObject(getUserRoutingRedisKey(chatMessage.getTargetId()));
            if (Strings.isNotBlank(userRoutingKey)) {
                // 目标用户连接在其他服务 通过mq传递消息
                log.info("userRoutingKey={},", userRoutingKey);
                rabbitMQService.sendMessage(userRoutingKey, JSON.toJSONString(chatMessage));
            }
        }
    }

}
