package com.paofeng.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.paofeng.common.security.annotation.EnableCustomConfig;
import com.paofeng.common.security.annotation.EnableRyFeignClients;
import com.paofeng.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 代码生成
 *
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class PaoFengGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaoFengGenApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  代码生成模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
