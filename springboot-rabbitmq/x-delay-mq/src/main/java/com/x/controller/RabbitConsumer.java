package com.x.controller;

import com.rabbitmq.client.Channel;
import com.x.config.RabbitConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author lryepoch
 * @date 2021/4/27 16:35
 * @description TODO activeMQ消费者
 */
@Component
@Slf4j
public class RabbitConsumer {

    /**
    * 接收消息
    * @param object 监听的内容
    */
    @RabbitListener(queues = RabbitConst.DELAY_QUEUE)
    public void cfgUserReceiveDelay(Object object, Message message, Channel channel) throws IOException {
        //手动ACK
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        try {
            log.info("接受消息:{}", object.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            /**
             * basicRecover方法是进行补发操作，
             * 其中的参数如果为true，是把消息退回到queue，但是有可能被其它的consumer(集群)接收到，
             * 设置为false是只补发给当前的consumer
             */
            channel.basicRecover(false);
        }
    }
}
