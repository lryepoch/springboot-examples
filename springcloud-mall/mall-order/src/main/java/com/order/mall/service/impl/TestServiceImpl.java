package com.order.mall.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.order.mall.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author lryepoch
 * @date 2021/3/23 16:48
 * @description TODO 该类的message()方法上加上@SentinelResource(“message”)注解，表示资源名为messge。
 *                   采用了@SentinelResource来定义资源点，然后通过Sentinel控制台对资源设置规则。
 */
@Service
public class TestServiceImpl implements TestService {

    @SentinelResource("message")
    public void message() {
        System.out.println("message");
    }
}
