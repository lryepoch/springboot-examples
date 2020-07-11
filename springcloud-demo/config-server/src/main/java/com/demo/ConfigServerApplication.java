package com.demo;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author lryepoch
 * @date 2020/7/8 19:45
 * @description TODO 同一个集群里的所有微服务都从同一份配置文件获取配置信息，大大节约了开发、上线和重新部署的时间
 */
@SpringBootApplication
@EnableConfigServer  //是个配置服务器
@EnableDiscoveryClient
@EnableEurekaClient
public class ConfigServerApplication {
    public static void main(String[] args) {
        int port = 8030;
        if (!NetUtil.isUsableLocalPort(port)){
            System.err.printf("端口%d被占用了，无法启动%n",port);
            System.exit(1);
        }
        new SpringApplicationBuilder(ConfigServerApplication.class).properties("server.port="+port).run(args);
    }
}
