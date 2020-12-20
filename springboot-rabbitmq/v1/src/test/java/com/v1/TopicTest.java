package com.v1;

import com.v1.topic.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lryepoch
 * @date 2020/5/25 17:41
 * @description TODO 发送send1会匹配到topic.#和topic.message 两个Receiver都可以收到消息，发送send2只有topic.#可以匹配所有 只有Receiver2监听到消息
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicTest {

    @Autowired
    private TopicSender sender;

//    @Test
//    public void topic(){
//        sender.send();
//    }

    @Test
    public void topic1(){
        sender.send1();
    }

    @Test
    public void topic2(){
        sender.send2();
    }
}
