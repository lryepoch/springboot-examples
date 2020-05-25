package com.crud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(value = "com.crud.mapper")//手动指定application类要扫描哪些包下的注解
public class Application {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}

}
