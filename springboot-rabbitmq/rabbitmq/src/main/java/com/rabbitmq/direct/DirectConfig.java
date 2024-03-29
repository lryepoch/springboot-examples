package com.rabbitmq.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lryepoch
 * @date 2020/12/18 17:14
 * @description  路由模式 是可以根据 "路由键" 选择性给多个消费者发送消息的模式，它包含一个生产者、两个消费者、两个队列和一个交换机。
 *               两个消费者同时绑定到不同的队列上去，两个队列通过路由键绑定到交换机上去，生产者发送消息到交换机，交换机通过路由键转发到不同队列，队列绑定的消费者接收并消费消息。
 *
 *               添加路由模式相关Java配置，创建一个名为exchange.direct的交换机、一个生产者、两个消费者和两个匿名队列，队列通过路由键都绑定到交换机，
 *               队列1的路由键为orange和black，队列2的路由键为green和black；
 *
 *               路由模式是相等匹配，通配符模式是通配符匹配；
 *
 */
@Configuration
public class DirectConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("exchange.direct");
    }

    @Bean
    public Queue directQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue directQueue2() {
        return new AnonymousQueue();
    }

    /**
    * queue1的路由键
    */
    @Bean
    public Binding directBinding1a(DirectExchange direct, Queue directQueue1){
        return BindingBuilder.bind(directQueue1).to(direct).with("orange");
    }

    /**
    * queue1的路由键
    */
    @Bean
    public Binding directBinding1b(DirectExchange direct, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(direct).with("black");
    }

    /**
    * queue2的路由键
    */
    @Bean
    public Binding directBinding2a(DirectExchange direct, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(direct).with("green");
    }

    /**
    * queue2的路由键
    */
    @Bean
    public Binding directBinding2b(DirectExchange direct, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(direct).with("black");
    }

    @Bean
    public DirectReceiver receiver() {
        return new DirectReceiver();
    }


    @Bean
    public DirectSender directSender() {
        return new DirectSender();
    }
}
