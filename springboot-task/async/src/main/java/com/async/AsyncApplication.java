package com.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
* @Description  异步任务
 *              异步：就是当执行多个任务时，不是一个一个接着执行，而是一起执行，所用的时间比较短）
 *              使用场景：处理log日志，发送邮件，发送短信等。
 *
 *              对于异步需要有返回值结果的处理：可以通过Future获取异步处理的返回结果,注意该类有@Async注解。
 *                                           Future类表示异步计算的未来结果 ，将处理完的结果放入Future中。
 *
 *
* @Author       lryepoch
* @CreateDate   2020/11/27 19:14
*/
@EnableAsync
@SpringBootApplication
public class AsyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class, args);
    }
}
