package com.jsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jsp.bean.Category;
import com.jsp.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class CategoryController {

    @Autowired(required = false)
    CategoryMapper categoryMapper;

    /**
     * 查询所有记录
     *
     */
    @GetMapping("/listCategory")
    public String listCategory(Model model, @RequestParam(value = "start", defaultValue = "1") int start,
                               @RequestParam(value = "size", defaultValue = "5") int size) {

        PageHelper.startPage(start, size, "id desc");
        List<Category> list = categoryMapper.findAll();
        PageInfo pageInfo = new PageInfo(list, 5);

//		return pageInfo;
        model.addAttribute("page", pageInfo);
        return "listCategory";
    }

    /**
     * 添加记录
     *
     */
    @PostMapping("/addCategory")
    public String addCategory(Category category) {
        categoryMapper.save(category);
        return "redirect:listCategory";
    }

    /**
     * 修改记录时先查询出待修改的记录
     *
     */
    @GetMapping("/getCategory")
    public String getCategory(int id, Model model) {
        Category category = categoryMapper.get(id);
        model.addAttribute("category", category);
        return "editCategory";
    }

    /**
     * 提交修改
     *
     */
    @PostMapping("/updateCategory")
    public String updateCategory(Category category) {
        categoryMapper.update(category);
        return "redirect:listCategory";
    }

    /**
     * 删除记录
     *
     */
    @GetMapping("/deleteCategory")
    public String deleteCategory(int id) {
        categoryMapper.delete(id);
        return "redirect:listCategory";
    }

}
