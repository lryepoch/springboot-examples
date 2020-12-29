package com.plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lryepoch
 * @date 2020/12/24 10:39
 * @description TODO mybatis-plus
 */
@SpringBootApplication
@MapperScan("com.plus.dao")
public class PlusApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlusApplication.class, args);
    }
}
