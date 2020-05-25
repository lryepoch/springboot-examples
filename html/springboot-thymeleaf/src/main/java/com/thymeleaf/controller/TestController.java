package com.thymeleaf.controller;

import com.thymeleaf.pojo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TestController {
 
    @RequestMapping("/test")
    public String test(Model m) {
    	String htmlContent = "<p style='color:red'> 红色文字</p>";
    	Product currentProduct =new Product(5,"product e", 200);
    	boolean testBoolean = true;
    	
    	Date now = new Date();
    	
    	List<Product> ps = new ArrayList<>();
    	ps.add(new Product(1,"product a", 50));
    	ps.add(new Product(2,"product b", 100));
    	ps.add(new Product(3,"product c", 150));
    	ps.add(new Product(4,"product d", 200));
    	ps.add(currentProduct);
    	ps.add(new Product(6,"product f", 200));
    	ps.add(new Product(7,"product g", 200));    	
    	
    	m.addAttribute("now", now);
    	m.addAttribute("ps", ps);
    	m.addAttribute("htmlContent", htmlContent);
    	m.addAttribute("currentProduct", currentProduct);
    	m.addAttribute("testBoolean", testBoolean);
    	

        return "test";
    }
}