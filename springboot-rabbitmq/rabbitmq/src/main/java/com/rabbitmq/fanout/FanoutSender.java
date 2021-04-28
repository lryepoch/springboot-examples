package com.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lryepoch
 * @date 2020/12/18 16:51
 * @description TODO 生产者通过send方法向交换机exchange.fanout中发送消息，消息中包含一定数量的.号；
 */
public class FanoutSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutSender.class);

    @Autowired
    private RabbitTemplate template;

    private static final String exchangeName = "exchange.fanout";

    public void send(int index) {
        StringBuilder builder = new StringBuilder("Hello World");
        for (int i = 0; i < index; i++) {
            builder.append('.');
        }
        String message = builder.toString();
        template.convertAndSend(exchangeName, "", message);
        LOGGER.info("fanout生产者第{}次 Sent {}", index, message);
    }

}
