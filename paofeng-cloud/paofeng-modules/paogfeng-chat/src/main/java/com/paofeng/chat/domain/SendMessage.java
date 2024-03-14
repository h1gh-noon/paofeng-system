package com.paofeng.chat.domain;

import com.paofeng.common.core.annotation.Excel;
import com.paofeng.common.core.utils.bean.BeanUtils;
import com.paofeng.common.core.web.domain.AjaxResult;

import java.util.Date;
import java.util.List;

public class SendMessage<T> {

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
     * 发送者
     */
    @Excel(name = "接收者")
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
     * 类型 默认私聊
     */
    @Excel(name = "类型 默认 1私聊 2系统消息 3订单状态消息")
    private String type;

    @Excel(name = "时间")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Date getCreateTime() {
        return createTime;
    }

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
        return "SendMessage{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", content='" + content + '\'' +
                ", data='" + data + '\'' +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public static AjaxResult getResult(ChatMessage message) {
        SendMessage sendMessage = new SendMessage();
        BeanUtils.copyBeanProp(sendMessage, message);
        sendMessage.setTargetId(null);
        return AjaxResult.successCode(sendMessage);
    }

    public static AjaxResult getReply(Long oldId, ChatMessage message) {
        SendMessage<Long> sendMessage = new SendMessage<>();
        sendMessage.setId(message.getId());
        sendMessage.setData(oldId);
        sendMessage.setCreateTime(message.getCreateTime());
        sendMessage.setType(ChatMessage.TYPE_REPLY);
        return AjaxResult.successCode(sendMessage);
    }
    public static AjaxResult getSync(List<SendMessage> messages) {
        SendMessage<List<SendMessage>> sendMessage = new SendMessage<>();
        sendMessage.setData(messages);
        sendMessage.setType(ChatMessage.TYPE_SYNC_CHAT);
        return AjaxResult.successCode(sendMessage);
    }
}
