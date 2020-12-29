package com.how2j.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.how2j.springboot.pojo.Category;

@Mapper
public interface CategoryMapper {
	
	/**
	 * 基于注解方式
	 * @return
	 */
	//@Select("select * from category_")
	
	/**
	 * xml配置方式
	 * @return
	 */
	public List<Category> findAll();

}
