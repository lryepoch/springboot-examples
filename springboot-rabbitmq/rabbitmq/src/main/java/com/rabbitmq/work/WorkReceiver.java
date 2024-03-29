package com.rabbitmq.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @author lryepoch
 * @date 2020/12/18 16:43
 * @description TODO 两个消费者从队列work.hello中获取消息，名称分别为instance 1和instance 2，消息中包含.号越多，耗时越长；
 */
@RabbitListener(queues = "work.hello")
public class WorkReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkReceiver.class);

    private final int instance;

    public WorkReceiver(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(String in){
        LOGGER.info("work消费者 {} Received {}", this.instance, in);
    }
}
