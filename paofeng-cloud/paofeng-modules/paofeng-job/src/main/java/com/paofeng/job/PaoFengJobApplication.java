package com.paofeng.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.paofeng.common.security.annotation.EnableCustomConfig;
import com.paofeng.common.security.annotation.EnableRyFeignClients;
import com.paofeng.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 *
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class PaoFengJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaoFengJobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
