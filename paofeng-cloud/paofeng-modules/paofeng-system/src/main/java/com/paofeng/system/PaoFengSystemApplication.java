package com.paofeng.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.paofeng.common.security.annotation.EnableCustomConfig;
import com.paofeng.common.security.annotation.EnableRyFeignClients;
import com.paofeng.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 系统模块
 *
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class PaoFengSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaoFengSystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  系统模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
