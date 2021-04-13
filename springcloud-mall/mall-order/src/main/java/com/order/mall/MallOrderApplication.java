package com.order.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author lryepoch
 * @date 2021/3/22 12:24
 * @description TODO
 */
@EnableDiscoveryClient//让注册中心能够发现
@EnableFeignClients//开启Fegin
@SpringBootApplication
public class MallOrderApplication {

    public static void main(String[] args){
        SpringApplication.run(MallOrderApplication.class, args);
    }

    /**
    * 通过RestTemplate来实现各个服务间的远程调用
    */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
