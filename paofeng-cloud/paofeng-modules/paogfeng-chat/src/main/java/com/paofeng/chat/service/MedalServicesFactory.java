package com.paofeng.chat.service;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class MedalServicesFactory {

    @Resource
    private Map<String, BaseWebsocketService> beanMap;

    public BaseWebsocketService getMedalService(String medalType) {
        if (medalType == null) {
            medalType = "1";
        }
        return beanMap.get(medalType);
    }

}
