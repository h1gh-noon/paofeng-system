package com.paofeng.chat.config;

import com.paofeng.chat.service.WebSocketService;
import com.paofeng.common.core.utils.uuid.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    public static final String topic = UUID.randomUUID().toString().replaceAll("-", "");

    @Value("${rabbitmq.exchange}")
    public String exchange;

    public static final String routingKey = UUID.randomUUID().toString().replaceAll("-", "");

    //初始化rabbitAdmin对象
    @Bean
    public Queue rabbitmqChatDirectQueue() {
        /**
         * 1、name:    队列名称
         * 2、durable: 是否持久化
         * 3、exclusive: 是否独享、排外的。如果设置为true，定义为排他队列。则只有创建者可以使用此队列。也就是private私有的。
         * 4、autoDelete: 是否自动删除。也就是临时队列。当最后一个消费者断开连接后，会自动删除。
         * */
        return new Queue(topic, true, false, false);
    }

    @Bean
    public DirectExchange rabbitmqChatDirectExchange() {
        //Direct交换机
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Binding bindDirect() {
        log.info("=======topic={},routingKey={}=============", topic, routingKey);
        //链式写法，绑定交换机和队列，并设置匹配键
        return BindingBuilder
                //绑定队列
                .bind(rabbitmqChatDirectQueue())
                //到交换机
                .to(rabbitmqChatDirectExchange())
                //并设置匹配键
                .with(routingKey);
    }

}
