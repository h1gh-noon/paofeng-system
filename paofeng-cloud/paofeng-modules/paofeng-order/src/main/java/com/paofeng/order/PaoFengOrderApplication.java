package com.paofeng.order;

import com.paofeng.common.security.annotation.EnableCustomConfig;
import com.paofeng.common.security.annotation.EnableRyFeignClients;
import com.paofeng.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * order模块
 *
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class PaoFengOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaoFengOrderApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  订单模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
