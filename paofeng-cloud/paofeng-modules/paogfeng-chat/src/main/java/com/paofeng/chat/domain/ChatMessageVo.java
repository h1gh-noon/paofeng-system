package com.paofeng.chat.domain;

import com.paofeng.common.core.annotation.Excel;
import com.paofeng.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 聊天对象 chat_message
 *
 * @author paofeng
 * @date 2024-02-22
 */
public class ChatMessageVo extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @Excel(name = "id")
    private Long id;

    /**
     * 发送者
     */
    @Excel(name = "发送者id")
    private Long senderId;


    /**
     * 发送者
     */
    @Excel(name = "发送者")
    private String senderUser;

    /**
     * targetId 接收者id
     */
    @Excel(name = "接收者id")
    private Long targetId;


    /**
     * targetId 接收者username
     */
    @Excel(name = "接收者")
    private String targetUser;



    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 内容
     */
    @Excel(name = "时间")
    private Date createTime;

    /**
     * 类型 默认私聊
     */
    @Excel(name = "类型 默认私聊")
    private String type;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(String senderUser) {
        this.senderUser = senderUser;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("content", getContent())
                .append("senderId", getSenderId())
                .append("targetId", getTargetId())
                .append("createTime", getCreateTime())
                .append("type", getType())
                .toString();
    }
}
