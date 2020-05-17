package com.hello.web;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {
	
	@RequestMapping("/jsp")
	public String hello(Model model){
		
		model.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));

		return "list";
		
	}
}
