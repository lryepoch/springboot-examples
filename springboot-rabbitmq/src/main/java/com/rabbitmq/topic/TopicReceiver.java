package com.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @author lryepoch
 * @date 2020/12/18 17:25
 * @description TODO 消费者从自己绑定的匿名队列中获取消息，由于该消费者可以从两个队列中获取并消费消息，可以看做两个消费者，名称分别为instance 1和instance 2；
 *
 * @description TODO @RabbitListener上设置持久化
 *                   @Queue: 当所有消费客户端连接断开后，是否自动删除队列
 *                           true：删除， false：不删除
 *                   @Exchange：当所有绑定队列都不再使用时，是否自动删除交换器
 *                              true：删除， false：不删除
 *                   @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "topic.message", autoDelete = "false"),
 *                                   exchange = @Exchange(value = "topicExchange", type = ExchangeTypes.TOPIC), key = "topic.message"))
 *
 */
public class TopicReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicReceiver.class);

    @RabbitListener(queues = "#{topicQueue1.name}")
    public void receive1(String in){
        receive(in, 1);
    }

    @RabbitListener(queues = "#{topicQueue2.name}")
    public void receive2(String in){
        receive(in, 2);
    }

    public void receive(String in, int receiver){
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info("instance {} [x] Received '{}'", receiver, in);
        doWork(in);
        watch.stop();
        LOGGER.info("instance {} [x] Done in {}s", receiver, watch.getTotalTimeSeconds());
    }

    private void doWork(String in){
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
