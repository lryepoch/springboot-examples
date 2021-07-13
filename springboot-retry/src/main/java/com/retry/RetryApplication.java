package com.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author lryepoch
 * @date 2021/7/13 9:11
 * @description TODO
 */
@SpringBootApplication
@EnableRetry  //支持方法和类、接口、枚举级别的重试
public class RetryApplication {
    public static void main(String[] args) {
        SpringApplication.run(RetryApplication.class, args);
    }
}
