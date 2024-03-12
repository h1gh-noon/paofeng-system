package com.paofeng.common.core.domain;

public class MQMessage <T>{

    private String type;

    private T data;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MQMessage{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
