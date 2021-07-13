package com.retry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteTimeoutException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author lryepoch
 * @date 2021/7/13 9:19
 * @description TODO
 */
@Service
public class RetryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryService.class);

    /**
     * @Retryable参数说明： value：指定异常进行重试
     * maxAttempts：重试次数，默认3次
     * backoff：补偿策略
     * include：和value一样，默认空，当exclude也为空时，所有异常都重试
     * exclude：指定异常不重试，默认空，当include也为空时，所有异常都重试
     */
    @Retryable(
            //指定发生的异常进行重试
            value = {RemoteAccessException.class, RemoteTimeoutException.class},
            //重试次数
            maxAttempts = 3,
            //补偿策略 delay：延迟多久执行补偿机制，multiplier：指定延迟的倍数，比如delay=5000,multiplier=2时，第一次重试为5秒后，第二次为10秒，第三次为20秒
            backoff = @Backoff(delay = 5000L, multiplier = 2)
    )
    public String call() {
        LOGGER.info("执行重试方法");
//        throw new RemoteAccessException("RPC访问异常");
        throw new RemoteTimeoutException("RPC调用超时异常");
    }

    @Recover
    public String recover(RemoteAccessException e) {
        LOGGER.info("最终重试失败，执行RemoteAccess补偿机制 error:{}", e.getMessage());
        return "ok";
    }

    @Recover
    public String recover(RemoteTimeoutException e) {
        LOGGER.info("最终重试失败，执行RemoteTimeout补偿机制 error:{}", e.getMessage());
        return "ok";
    }


    /**
     * 有几点需要注意的地方：
     *
     * 重试机制的service服务必须单独创建一个class，不能写在接口的实现类里，否则会抛出ex
     * @Retryable是以AOP实现的，所以如果@Retryable标记的方法被其他方法调用了，则不会进行重试
     * recover方法的返回值类型必须和call()方法的返回值类型一致
     */
}
