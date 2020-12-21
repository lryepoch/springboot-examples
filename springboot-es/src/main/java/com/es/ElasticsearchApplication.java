package com.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lryepoch
 * @date 2020/12/7 9:00
 * @description TODO es demo
 */
@SpringBootApplication
@MapperScan("com.es.dao")
public class ElasticsearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }
}
