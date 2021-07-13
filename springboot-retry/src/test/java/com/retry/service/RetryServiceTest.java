package com.retry.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 在IDEA里去掉@RunWith仍然能跑是因为在IDEA里识别为一个JUNIT的运行环境，相当于就是一个自识别的RUNWITH环境配置。
 * 但在其他IDE里并没有。 所以，为了你的代码能在其他IDE里边正常跑，建议还是加@RunWith
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RetryServiceTest {

    @Autowired
    private RetryService retryService;

    @Test
    public void testRetry() {
        String result = retryService.call();
        MatcherAssert.assertThat(result, Matchers.is("ok"));
    }
}
