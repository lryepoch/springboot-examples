package com.rabbitmq;

import com.rabbitmq.many.LrySender1;
import com.rabbitmq.many.LrySender2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lryepoch
 * @date 2020/5/25 16:51
 * @description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManyTest {
    @Autowired
    private LrySender1 lrySender1;

    @Autowired
    private LrySender2 lrySender2;

    @Test
    public void oneToMany() {
        for (int i=0;i<100;i++) {
            lrySender1.send(i);
        }
    }

    @Test
    public void manyToMany() {
        for (int i=0;i<100;i++) {
            lrySender1.send(i);
            lrySender2.send(i);
        }
    }
}
