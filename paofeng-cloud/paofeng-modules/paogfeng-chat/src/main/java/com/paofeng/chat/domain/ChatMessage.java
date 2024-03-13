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
public class ChatMessage<T> extends BaseEntity {
    private static final long serialVersionUID = 1L;

    // 1私聊
    public static final String TYPE_CHAT = "1";

    // 2系统消息
    public static final String TYPE_SYS = "2";

    // 3订单状态消息
    public static final String TYPE_ORDER = "3";

    // 4创建聊天框
    public static final String TYPE_CHAT_WINDOW = "4";

    // 客户端请求联系人信息
    public static final String OPTION_GET_FRIEND = "getFriend";

    // 服务器响应消息发送成功
    public static final String TYPE_REPLY = "reply";

    /**
     * id
     */
    @Excel(name = "id")
    private Long id;

    /**
     * 发送者
     */
    @Excel(name = "发送者")
    private Long senderId;

    /**
     * targetId 接收者id
     */
    @Excel(name = "目标id")
    private Long targetId;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 数据
     */
    @Excel(name = "数据")
    private T data;

    /**
     * 内容
     */
    @Excel(name = "时间")
    private Date createTime;

    /**
     * 类型 默认私聊
     */
    @Excel(name = "类型 默认 1私聊 2系统消息 3订单状态消息 4创建聊天框")
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("content", getContent())
                .append("data", getData())
                .append("senderId", getSenderId())
                .append("targetId", getTargetId())
                .append("createTime", getCreateTime())
                .append("type", getType())
                .toString();
    }
}
