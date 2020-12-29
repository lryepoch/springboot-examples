package com.crud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.crud.bean.Category;

/**
 * @author lryepoch
 * @date 2020/12/29 15:09
 * @description interface 成员默认修饰符public static final；方法默认修饰符public abstract
 */
@Mapper
public interface CategoryMapper {
	
	@Select("select * from category_")
	List<Category> findAll();

	@Insert("insert into category_(name) values(#{name})")
	int save(Category category);

	@Select("select * from category_ where id=#{id}")
	Category get(int id);

	@Update("update category_ set name=#{name} where id=#{id}")
	int update(Category category);

	@Delete("delete from category_ where id=#{id}")
	void delete(int id);
}
