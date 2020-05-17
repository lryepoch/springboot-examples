package com.hello.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理：@ControllerAdvice + @ExceptionHandler
 * @author Administrator
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request,Exception e) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("exception", e);
		modelAndView.addObject("url", request.getRequestURL());
		modelAndView.setViewName("fault");
		
		return modelAndView;
		
	}

}
