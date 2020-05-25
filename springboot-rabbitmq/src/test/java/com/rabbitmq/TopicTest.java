package com.rabbitmq;

import com.rabbitmq.topic.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lryepoch
 * @date 2020/5/25 17:41
 * @description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicTest {

    @Autowired
    private TopicSender sender;

    @Test
    public void topic(){
        sender.send();
    }

    @Test
    public void topic1(){
        sender.send1();
    }

    @Test
    public void topic2(){
        sender.send2();
    }
}
