package com.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crud.bean.Category;
import com.crud.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryMapper categoryMapper;
	
	/**
	 * 查询所有记录
	 * @param model
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listCategory")
//	@ResponseBody
	public String listCategory(Model model,@RequestParam(value = "start", defaultValue = "1") int start,
											@RequestParam(value = "size", defaultValue = "5") int size) throws Exception{
		
		PageHelper.startPage(start,size,"id desc");
		List<Category> list = categoryMapper.findAll();
		PageInfo pageInfo = new PageInfo(list,5);
		
//		return pageInfo;
		model.addAttribute("page", pageInfo);
		return "listCategory";
	}
	
	/**
	 * 添加记录
	 * @param category
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addCategory")
	public String addCategory(Category category) throws Exception{
		categoryMapper.save(category);
		return "redirect:listCategory";
	}

	/**
	 * 修改记录时先查询出待修改的记录
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCategory")
	public String getCategory(int id, Model model) throws Exception{
		Category category = categoryMapper.get(id);
		model.addAttribute("category", category);
		return "editCategory";
	}
	
	/**
	 * 提交修改
	 * @param category
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateCategory")
	public String updateCategory(Category category) throws Exception{
		categoryMapper.update(category);
		return "redirect:listCategory";
	}
	
	/**
	 * 删除记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteCategory")
	public String deleteCategory(int id) throws Exception{
		categoryMapper.delete(id);
		return "redirect:listCategory";
	}
}
