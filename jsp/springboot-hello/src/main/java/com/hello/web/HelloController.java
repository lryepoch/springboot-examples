package com.hello.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController = @Controller + @ResponseBody
@RestController
public class HelloController {
	
	@RequestMapping(value = "hello")
	public String hello(){
		
		//返回的是json，不需要写一个页面
		return "Hello Spring boot!";
	}

}
