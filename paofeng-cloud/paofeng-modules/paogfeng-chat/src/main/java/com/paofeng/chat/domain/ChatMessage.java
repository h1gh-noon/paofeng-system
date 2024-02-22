package com.paofeng.chat.domain;

import com.paofeng.common.core.annotation.Excel;
import com.paofeng.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 聊天对象 chat_message
 *
 * @author paofeng
 * @date 2024-02-22
 */
public class ChatMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "id")
    private String id;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 发送者
     */
    @Excel(name = "发送者")
    private String sender;

    /**
     * 接收者
     */
    @Excel(name = "接收者")
    private String receiver;

    /**
     * 类型 默认私聊
     */
    @Excel(name = "类型 默认私聊")
    private String type;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("content", getContent())
                .append("sender", getSender())
                .append("receiver", getReceiver())
                .append("createTime", getCreateTime())
                .append("type", getType())
                .toString();
    }
}
