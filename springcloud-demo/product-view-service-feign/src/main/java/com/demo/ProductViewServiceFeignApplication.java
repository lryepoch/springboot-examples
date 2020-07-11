package com.demo;

import brave.sampler.Sampler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lryepoch
 * @date 2020/7/8 14:09
 * @description TODO Feign 是对 Ribbon的封装，使用注解的方式，调用起来更简单。。。 也是主流的方式~
 *                  把现成的 视图微服务-Feign 改造成配置客户端，使得其可以从配置服务器上获取版本信息~
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient //表示用于发现eureka 注册中心的微服务
@EnableFeignClients   //用于使用 Feign 方式
@EnableCircuitBreaker //把信息共享给监控中心 hystrix-dashboard
public class ProductViewServiceFeignApplication {
    public static void main(String[] args) {
        //判断rabbitMQ是否启动
        int rabbitMQPort=5672;
        if (NetUtil.isUsableLocalPort(rabbitMQPort)){
            System.err.printf("未在端口%d发现rabbitMQ服务，请检查rabbitMQ是否启动", rabbitMQPort);
            System.exit(1);
        }

        int port = 0;
        int defaultPort = 8012;
        Future<Integer> future = ThreadUtil.execAsync(()->{
            int p=0;
            System.out.println("请于5秒钟内输入端口号, 推荐8012，超过5秒将默认使用 " + defaultPort);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String strPort = scanner.nextLine();
                if (!NumberUtil.isInteger(strPort)){
                    System.err.println("只能是数字");
                    continue;
                }
                else{
                    p = Convert.toInt(strPort);
                    scanner.close();
                    break;
                }
            }
            return p;
        });

        try {
            port = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e){
            port = defaultPort;
        }

        if (!NetUtil.isUsableLocalPort(port)){
            System.err.printf("端口%d被占用了，无法启动%n",port);
            System.exit(1);
        }

        new SpringApplicationBuilder(ProductViewServiceFeignApplication.class).properties("server.port=" + port).run(args);
    }

    //服务链路追踪
    //ALWAYS_SAMPLE 表示持续抽样
    @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }

}
