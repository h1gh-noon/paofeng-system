package com.paofeng.chat;

import com.paofeng.common.security.annotation.EnableCustomConfig;
import com.paofeng.common.security.annotation.EnableRyFeignClients;
import com.paofeng.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class PaoFengChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaoFengChatApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  chat模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }

}
