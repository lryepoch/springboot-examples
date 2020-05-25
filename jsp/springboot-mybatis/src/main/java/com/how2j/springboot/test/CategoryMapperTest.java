package com.how2j.springboot.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.how2j.springboot.Application;
import com.how2j.springboot.mapper.CategoryMapper;
import com.how2j.springboot.pojo.Category;

/**
 * springboot单元测试
 * 
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CategoryMapperTest{

	@Autowired
	CategoryMapper categoryMapper;
	
	@Test
	public void testFindAll() {
		List<Category> categories = categoryMapper.findAll();
		for(Category c:categories){
			System.out.println("c.getId="+c.getId()+"，c.getName="+c.getName());
		}
		
	}

}
