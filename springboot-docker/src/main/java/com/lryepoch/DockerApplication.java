package com.lryepoch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2020/11/23 9:16
 * @description TODO springboot集成docker
 */
@SpringBootApplication
@RestController
public class DockerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }

    @GetMapping(value = "/test")
    public String test(){
        return "Hello Docker World!";
    }
}
