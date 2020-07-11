package com.demo;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lryepoch
 * @date 2020/7/8 14:09
 * @description Ribbon 是使用 restTemplate 进行调用，并进行客户端负载均衡。 什么是客户端负载均衡呢？ 在前面 注册数据微服务 里，
 *              注册了8001和8002两个微服务， Ribbon 会从注册中心获知这个信息，然后由 Ribbon 这个客户端自己决定是调用哪个，这个就叫做客户端负载均衡。
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient //表示用于发现eureka 注册中心的微服务
public class ProductViewServiceRibbonApplication {
    public static void main(String[] args) {
        int port = 0;
        int defaultPort = 8010;
        Future<Integer> future = ThreadUtil.execAsync(()->{
            int p=0;
            System.out.println("请于5秒钟内输入端口号, 推荐8010，超过5秒将默认使用 " + defaultPort);
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

        new SpringApplicationBuilder(ProductViewServiceRibbonApplication.class).properties("server.port=" + port).run(args);

    }

    /**
    * @description 用 restTemplate 这个工具来做负载均衡
    * @author lryepoch
    * @date 2020/7/8 15:19
    *
    */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
