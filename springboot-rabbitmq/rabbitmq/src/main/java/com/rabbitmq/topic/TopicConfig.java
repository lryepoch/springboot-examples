package com.rabbitmq.topic;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lryepoch
 * @date 2020/12/18 17:22
 * @description  通配符模式 是可以根据 "路由键" 匹配规则选择性给多个消费者发送消息的模式，它包含一个生产者、两个消费者、两个队列和一个交换机。
 *               两个消费者同时绑定到不同的队列上去，两个队列通过路由键匹配规则绑定到交换机上去，生产者发送消息到交换机，交换机通过路由键匹配规则转发到不同队列，队列绑定的消费者接收并消费消息。
 *
 *               添加通配符模式相关Java配置，创建一个名为exchange.topic的交换机、一个生产者、两个消费者和两个匿名队列。
 *               匹配*.orange.*和*.*.rabbit发送到队列1；匹配lazy.#发送到队列2
 *
 *               符号*：只能匹配一个词；
 *               符号#：能匹配一个或者多个词（每个词之间‘.’分隔）
 *
 *               路由模式是相等匹配，通配符模式是通配符匹配；
 *
 */
@Configuration
public class TopicConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("exchange.topic");
    }

    @Bean
    public Queue topicQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue topicQueue2() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding topicBinding1a(TopicExchange topic, Queue topicQueue1) {
        return BindingBuilder.bind(topicQueue1).to(topic).with("*.orange.*");
    }

    @Bean
    public Binding topicBinding1b(TopicExchange topic, Queue topicQueue1) {
        return BindingBuilder.bind(topicQueue1).to(topic).with("*.*.rabbit");
    }

    @Bean
    public Binding topicBinding2a(TopicExchange topic, Queue topicQueue2) {
        return BindingBuilder.bind(topicQueue2).to(topic).with("lazy.#");
    }

    @Bean
    public TopicReceiver topicReceiver() {
        return new TopicReceiver();
    }

    @Bean
    public TopicSender topicSender() {
        return new TopicSender();
    }

}
