package com.rabbitmq.v2.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lryepoch
 * @date 2020/12/18 16:39
 * @description TODO 生产者通过send方法向队列work.hello中发送消息，消息中包含一定数量的.号；
 */
public class WorkSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkSender.class);

    @Autowired
    private RabbitTemplate template;

    private static final String queueName = "work.hello";

    public void send(int index){
        StringBuilder builder = new StringBuilder("Hello");
        int limitIndex = index%3+1;
        for (int i=1;i<limitIndex;i++){
            builder.append('.');
        }
        builder.append(index+1);
        String message = builder.toString();
        template.convertAndSend(queueName, message);
        LOGGER.info(" [x] Sent '{}'", message);
    }
}
