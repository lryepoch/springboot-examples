package com.rabbitmq;


import com.rabbitmq.direct.many.LrySender1;
import com.rabbitmq.direct.many.LrySender2;
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

    /**
    * @description 一对多使用，接收端仍然会均匀接收到消息
    * @author lryepoch
    * @date 2020/12/4 15:57
    *
    */
    @Test
    public void oneToMany() {
        for (int i=0;i<10;i++) {
            lrySender1.send(i);
        }
    }

    /**
    * @description 多对多使用，接收端仍然会均匀接收到消息
    * @author lryepoch
    * @date 2020/12/4 15:57
    *
    */
    @Test
    public void manyToMany() {
        for (int i=0;i<10;i++) {
            lrySender1.send(i);
            lrySender2.send(i);
        }
    }
}
