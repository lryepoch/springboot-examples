package com.v1;

import com.v1.fanout.FanoutSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lryepoch
 * @date 2020/5/25 16:10
 * @description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutTest {
    @Autowired
    private FanoutSender sender;

    @Test
    public void fanoutSender(){
        sender.send();
    }
}
