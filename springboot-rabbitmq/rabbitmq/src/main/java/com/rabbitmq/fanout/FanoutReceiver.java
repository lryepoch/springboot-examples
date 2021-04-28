package com.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @author lryepoch
 * @date 2020/12/18 16:51
 * @description TODO 消费者从绑定的匿名队列中获取消息，消息中包含.号越多，耗时越长，由于该消费者可以从两个队列中获取并消费消息，可以看做两个消费者，名称分别为instance 1和instance 2；
 */
public class FanoutReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutReceiver.class);

    @RabbitListener(queues = "#{fanoutQueue1.name}")
    public void receive1(String in){
        receive(1, in);
    }

    @RabbitListener(queues = "#{fanoutQueue2.name}")
    public void receive2(String in){
        receive(2, in);
    }

    private void receive(int receiver, String in) {
        LOGGER.info("fanout消费者 {} Received {}", receiver, in);
    }
}
