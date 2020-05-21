package com.hello.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;

@Controller
public class JspController {
	
	@RequestMapping(value = "jsp")
	public String hello(Model model){
		
		model.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));

		return "list";
		
	}
}
