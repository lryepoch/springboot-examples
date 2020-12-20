package com.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/20 15:55
 * @Description 1.实现MongoRepository；2.注入MongodbTemplate
 */
@SpringBootApplication
public class MongodbApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongodbApplication.class, args);
    }
}
