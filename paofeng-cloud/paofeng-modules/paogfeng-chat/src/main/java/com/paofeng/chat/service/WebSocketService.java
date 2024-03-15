package com.paofeng.chat.service;


import com.alibaba.fastjson2.JSON;
import com.paofeng.chat.config.RabbitConfig;
import com.paofeng.chat.config.WebSocketConfig;
import com.paofeng.chat.domain.ChatMessage;
import com.paofeng.chat.domain.SocketClient;
import com.paofeng.common.redis.service.RedisService;
import com.paofeng.system.api.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/websocket", subprotocols = {"protocol"}, configurator = WebSocketConfig.class)
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    private static MedalServicesFactory medalServicesFactory;

    @Resource
    public void setMedalServicesFactory (MedalServicesFactory medalServicesFactory) {
        WebSocketService.medalServicesFactory = medalServicesFactory;
    }

    private static RedisService redisService;

    @Resource
    public void setRedisService(RedisService redisService) {
        WebSocketService.redisService = redisService;
    }

    // concurrent包的线程安全Map，用来存放每个客户端对应的WebSocketServer对象。
    private static final ConcurrentHashMap<Long, SocketClient> webSocketMap = BaseWebsocketService.webSocketMap;


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
        LoginUser loginUser = BaseWebsocketService.getLoginUser(session);
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
            LoginUser loginUser = BaseWebsocketService.getLoginUser(session);
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
            // 工厂模式
            BaseWebsocketService medalService = medalServicesFactory.getMedalService(options);
            // 执行对应的业务逻辑
            medalService.execute(chatMessage, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error:{},msg:{}", userName, error.getMessage());
        error.printStackTrace();
    }


    private void setUserRoutingKey(Long userId) {
        redisService.setCacheObject(BaseWebsocketService.USER_ROUTING_KEY + ":USER_ID_" + userId,
                RabbitConfig.routingKey);
    }

    private void delUserRoutingKey(Long userId) {
        redisService.deleteObject(BaseWebsocketService.USER_ROUTING_KEY + ":USER_ID_" + userId);
    }


}
