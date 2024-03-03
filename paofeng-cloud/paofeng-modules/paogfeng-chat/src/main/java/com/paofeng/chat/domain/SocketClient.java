package com.paofeng.chat.domain;


import com.paofeng.system.api.model.LoginUser;

import javax.websocket.Session;

public class SocketClient {

    // 与客户端的连接
    private Session session;

    private String uri;

    private LoginUser loginUser;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }
}
