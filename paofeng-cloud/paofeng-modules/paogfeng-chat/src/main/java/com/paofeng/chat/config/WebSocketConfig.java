package com.paofeng.chat.config;

import com.paofeng.common.core.constant.SecurityConstants;
import com.paofeng.common.core.constant.TokenConstants;
import com.paofeng.common.core.utils.StringUtils;
import com.paofeng.common.security.auth.AuthUtil;
import com.paofeng.system.api.model.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    public static final String LOGIN_USER_KEY = "LoginUser";

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    /**
     * 建立连接前
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        final Map<String, Object> userProperties = sec.getUserProperties();
        Map<String, List<String>> headers = request.getHeaders();
        List<String> secs = request.getHeaders().get("Sec-WebSocket-Protocol");
        response.getHeaders().put("Sec-WebSocket-Protocol", secs);
        super.modifyHandshake(sec, request, response);

        String token = headers.get("Sec-WebSocket-Protocol").get(0);
        if (token != null) {
            if (token.startsWith(TokenConstants.PREFIX)) {
                token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
            }
            LoginUser loginUser = AuthUtil.getLoginUser(token);
            if (loginUser != null) {
                userProperties.put(SecurityConstants.AUTHORIZATION_HEADER, token);
                userProperties.put(LOGIN_USER_KEY, loginUser);
            }
        }
    }

    /**
     * 初始化端点对象,也就是被@ServerEndpoint所标注的对象
     */
    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return super.getEndpointInstance(clazz);
    }
}
