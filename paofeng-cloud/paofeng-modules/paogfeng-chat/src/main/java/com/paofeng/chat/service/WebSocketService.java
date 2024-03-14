package com.paofeng.chat.service;


import com.alibaba.fastjson2.JSON;
import com.paofeng.chat.config.RabbitConfig;
import com.paofeng.chat.config.WebSocketConfig;
import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.SendMessage;
import com.paofeng.chat.domain.SocketClient;
import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.domain.R;
import com.paofeng.common.core.web.domain.AjaxResult;
import com.paofeng.common.redis.service.RedisService;
import com.paofeng.system.api.RemoteUserService;
import com.paofeng.system.api.domain.UserRelation;
import com.paofeng.system.api.model.LoginUser;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/websocket", subprotocols = {"protocol"}, configurator = WebSocketConfig.class)
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    private static IChatMessageService chatMessageService;

    @Resource
    public void setIChatMessageService(IChatMessageService chatMessageService) {
        WebSocketService.chatMessageService = chatMessageService;
    }

    private static RedisService redisService;

    @Resource
    public void setRedisService(RedisService redisService) {
        WebSocketService.redisService = redisService;
    }

    private static RabbitMQService rabbitMQService;

    @Resource
    public void setRabbitMQService(RabbitMQService rabbitMQService) {
        WebSocketService.rabbitMQService = rabbitMQService;
    }

    private static RemoteUserService remoteUserService;

    @Resource
    public void setRemoteUserService(RemoteUserService remoteUserService) {
        WebSocketService.remoteUserService = remoteUserService;
    }

    public static final String USER_ROUTING_KEY = "UserRoutingKey";

    public static final String USER_FRIENDS = "UserFriends";

    // concurrent包的线程安全Map，用来存放每个客户端对应的WebSocketServer对象。
    private static final ConcurrentHashMap<Long, SocketClient> webSocketMap = new ConcurrentHashMap<>();


    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收userName
     */
    private String userName = "";

    /**
     * 接收userId
     */
    private Long userId = null;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        // "sec-websocket-protocol"
        LoginUser loginUser = getLoginUser(session);
        if (loginUser == null) {
            session.close();
            return;
        }
        this.session = session;
        // log.info("token={}",token);
        this.userName = loginUser.getUsername();
        this.userId = loginUser.getUserid();
        SocketClient client = new SocketClient();
        client.setSession(session);
        client.setUri(session.getRequestURI().toString());
        client.setLoginUser(loginUser);
        webSocketMap.put(this.userId, client);

        setUserRoutingKey(this.userId);
        log.info("----------------------------------------------------------------------------");
        log.info("用户连接:{}", userName);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (userId != null) {
            webSocketMap.remove(userId);
            delUserRoutingKey(userId);
        }
        log.info("用户退出:{}", userName);
    }

    /**
     * 接收客户端消息
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            LoginUser loginUser = getLoginUser(session);
            if (loginUser == null) {
                session.close();
                return;
            }
            log.info("收到{}消息:{}", userName, message);
            if (!JSON.isValidObject(message)) {
                return;
            }
            ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);
            chatMessage.setSenderId(loginUser.getUserid());
            String options = chatMessage.getType();
            if (options != null) {
                if (options.equals(ChatMessage.OPTION_GET_FRIEND)) {
                    // 请求联系人信息
                    getRelation();
                } else if (options.equals(ChatMessage.TYPE_SYNC_CHAT)) {
                    // 同步聊天记录
                    List<SendMessage> chatMessages = chatMessageService.selectChatMessageListByUser(chatMessage);
                    sendMessage(SendMessage.getSync(chatMessages));
                }
            } else {
                Long oldId = chatMessage.getId();
                chatMessageService.insertChatMessage(chatMessage);
                log.info("====={}", chatMessage);
                // Long targetId = chatMessage.getTargetId();
                // 检查用户关系 检查对方(接收者)是否有发送者好友
                // if (checkUserFriends(targetId, this.userId)) {
                sendMessage(SendMessage.getReply(oldId, chatMessage));
                sendCheckHandler(chatMessage);
                // } else {
                //     sendMessage(AjaxResult.error());
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRelation() throws IOException {
        Set<Long> userFriends = getUserFriends(userId);
        ChatMessage<List<UserRelation>> chatMessage = new ChatMessage<>();
        chatMessage.setType(ChatMessage.OPTION_GET_FRIEND);
        if (userFriends != null && !userFriends.isEmpty()) {
            R<List<UserRelation>> relationInfoRes =
                    remoteUserService.getRelationInfo(userFriends.toArray(new Long[]{}),
                            SecurityConstants.INNER);
            if (R.isSuccess(relationInfoRes)) {
                List<UserRelation> data = relationInfoRes.getData();
                chatMessage.setData(data);
            }
        }

        sendMessage(userId, SendMessage.getResult(chatMessage));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error:{},msg:{}", userName, error.getMessage());
        error.printStackTrace();
    }

    public static void sendCheckHandler(ChatMessage chatMessage) throws IOException {
        if (webSocketMap.containsKey(chatMessage.getTargetId())) {
            // 消息接收用户连接在此服务
            sendMessage(chatMessage.getTargetId(), SendMessage.getResult(chatMessage));
        } else {
            // 不在此服务 查询redis
            String userRoutingKey = getUserRoutingKey(chatMessage.getTargetId());
            if (Strings.isNotBlank(userRoutingKey)) {
                // 目标用户连接在其他服务 通过mq传递消息
                log.info("userRoutingKey={},", userRoutingKey);
                rabbitMQService.sendMessage(userRoutingKey, JSON.toJSONString(chatMessage));
            }
        }
    }

    /**
     * 主动推送消息
     */
    public void sendMessage(AjaxResult ajaxResult) throws IOException {
        sendMessage(JSON.toJSONString(ajaxResult));
    }

    /**
     * 主动推送消息
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
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
    private LoginUser getLoginUser(Session session) {
        return (LoginUser) session.getUserProperties().get(WebSocketConfig.LOGIN_USER_KEY);
    }

    private void setUserRoutingKey(Long userId) {
        redisService.setCacheObject(USER_ROUTING_KEY + ":USER_ID_" + userId, RabbitConfig.routingKey);
    }

    private void delUserRoutingKey(Long userId) {
        redisService.deleteObject(USER_ROUTING_KEY + ":USER_ID_" + userId);
    }

    public static String getUserRoutingKey(Long userId) {
        return redisService.getCacheObject(USER_ROUTING_KEY + ":USER_ID_" + userId);
    }

    public static Set<Long> getUserFriends(Long userId) {
        return redisService.getCacheSet(USER_FRIENDS + ":USER_ID_" + userId);
    }

    public static boolean checkUserFriends(Long userId, Long friendId) {
        if (userId == null || friendId == null) {
            return false;
        }
        Set<Long> userFriends = getUserFriends(userId);
        return userFriends.contains(friendId);
    }

    public static void setUserFriends(Long userId, Long friendId) {
        if (userId == null || friendId == null) {
            return;
        }
        Set<Long> userFriendsSet = getUserFriends(userId);
        userFriendsSet.add(friendId);
        redisService.setCacheSet(USER_FRIENDS + ":USER_ID_" + userId, userFriendsSet);

        Set<Long> friendsSet = getUserFriends(friendId);
        friendsSet.add(userId);
        redisService.setCacheSet(USER_FRIENDS + ":USER_ID_" + friendId, friendsSet);
    }
}
