package com.paofeng.chat.service.impl;

import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.service.BaseWebsocketService;
import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.domain.R;
import com.paofeng.common.redis.service.RedisService;
import com.paofeng.system.api.RemoteUserService;
import com.paofeng.system.api.domain.UserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service(ChatMessage.OPTION_GET_FRIEND)
public class GetFriendMedalService extends BaseWebsocketService {

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private RedisService redisService;


    @Override
    public void execute(ChatMessage chatMessage, Session session) {
        // 请求联系人信息
        Long userId = getLoginUser(session).getUserid();
        Set<Long> userFriends = redisService.getCacheSet(getUserFriendsRedisKey(userId));
        ChatMessage<List<UserRelation>> chatMessages = new ChatMessage<>();
        chatMessages.setType(ChatMessage.OPTION_GET_FRIEND);
        if (userFriends != null && !userFriends.isEmpty()) {
            R<List<UserRelation>> relationInfoRes =
                    remoteUserService.getRelationInfo(userFriends.toArray(new Long[]{}),
                            SecurityConstants.INNER);
            if (R.isSuccess(relationInfoRes)) {
                List<UserRelation> data = relationInfoRes.getData();
                chatMessages.setData(data);
            }
        }

        try {
            sendMessage(userId, ChatMessage.getResult(chatMessages));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
