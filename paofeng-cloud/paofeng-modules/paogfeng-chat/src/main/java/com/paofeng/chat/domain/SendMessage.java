package com.paofeng.chat.domain;

import com.paofeng.common.core.annotation.Excel;
import com.paofeng.common.core.utils.bean.BeanUtils;
import com.paofeng.common.core.web.domain.AjaxResult;

public class SendMessage {

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
     * 类型 默认私聊
     */
    @Excel(name = "类型 默认私聊")
    private String type;


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

    @Override
    public String toString() {
        return "SendMessage{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static AjaxResult getResult(ChatMessage message){
        SendMessage sendMessage = new SendMessage();
        BeanUtils.copyBeanProp(sendMessage, message);
        return AjaxResult.successCode(sendMessage);
    }
}
