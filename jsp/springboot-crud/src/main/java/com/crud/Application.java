package com.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(value = "com.crud.mapper")  //手动指定application类要扫描哪些包下的注解 或者在mapper接口方法中使用@Mapper
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}


/**
 * @author lryepoch
 * @date 2020/12/29 15:12
 * @description 项目访问url：http://localhost:8082/listCategory
 */