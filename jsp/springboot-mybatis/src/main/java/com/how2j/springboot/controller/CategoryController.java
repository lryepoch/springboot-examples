package com.how2j.springboot.controller;

import java.io.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.how2j.springboot.mapper.CategoryMapper;
import com.how2j.springboot.pojo.Category;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


/**
 * 基于mapper.xml文件而进行的springboot开发
 * 
 * @author Administrator
 *
 */
@Controller
public class CategoryController {

	@Autowired
	CategoryMapper categoryMapper;
	
	@RequestMapping("/listCategory")
	public String listCategory(Model model){
		
		List<Category> categories = categoryMapper.findAll();

		model.addAttribute("list", categories);
		
		return "listCategory";

	}

    @RequestMapping("/download")
	public void downloadTxt(HttpServletResponse response) {

        List<Category> categories = categoryMapper.findAll();

        String fileName = "1.txt";

        //text/plain ：纯文本格式
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        OutputStream outputStream = null;

        BufferedOutputStream buffer = null;

        try {
            outputStream = response.getOutputStream();

            buffer = new BufferedOutputStream(outputStream);

            for (Category category : categories) {
                buffer.write((category.getId() + " " + category.getName() + "\r\n").getBytes("UTF-8"));
            }

            buffer.flush();

            buffer.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

	}
}
