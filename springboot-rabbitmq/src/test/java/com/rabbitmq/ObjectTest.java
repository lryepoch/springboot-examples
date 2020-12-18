package com.rabbitmq;

import com.rabbitmq.v1.direct.object.ObjectSender;
import com.rabbitmq.v1.direct.object.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lryepoch
 * @date 2020/5/25 15:57
 * @description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectTest {

    @Autowired
    ObjectSender objectSender;

    @Test
    public void object(){
        User user = new User();
        user.setName("lry");
        user.setPassword("123456");
        objectSender.send(user);
    }
}
