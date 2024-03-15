package com.paofeng.chat.service;

import com.alibaba.fastjson2.JSON;
import com.paofeng.chat.config.WebSocketConfig;
import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.SocketClient;
import com.paofeng.common.core.web.domain.AjaxResult;
import com.paofeng.system.api.model.LoginUser;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseWebsocketService {

    public static final String USER_ROUTING_KEY = "UserRoutingKey";

    public static final String USER_FRIENDS = "UserFriends";


    // concurrent包的线程安全Map，用来存放每个客户端对应的WebSocketServer对象。
    protected static final ConcurrentHashMap<Long, SocketClient> webSocketMap = new ConcurrentHashMap<>();

    // 执行方法
    public abstract void execute(ChatMessage chatMessage, Session session);


    /**
     * 主动推送消息
     */
    public static void sendMessage(Session session, AjaxResult ajaxResult) throws IOException {
        sendMessage(session, JSON.toJSONString(ajaxResult));
    }

    /**
     * 主动推送消息
     */
    public static void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    /**
     * 向指定客户端发送消息
     */
    public static void sendMessage(Long userId, AjaxResult ajaxResult) throws IOException {
        sendMessage(userId, JSON.toJSONString(ajaxResult));
    }

    /**
     * 向指定客户端发送消息
     *
     * @param userId
     * @param message
     */
    public static void sendMessage(Long userId, String message) {
        try {
            SocketClient socketClient = webSocketMap.get(userId);
            if (socketClient != null) {
                socketClient.getSession().getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    // 获取用户
    public static LoginUser getLoginUser(Session session) {
        return (LoginUser) session.getUserProperties().get(WebSocketConfig.LOGIN_USER_KEY);
    }


    public static String getUserRoutingRedisKey(Long userId) {
        return USER_ROUTING_KEY + ":USER_ID_" + userId;
    }

    public static String getUserFriendsRedisKey(Long userId) {
        return USER_FRIENDS + ":USER_ID_" + userId;
    }

}
