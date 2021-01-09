package com.thymeleaf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.thymeleaf.mapper.CategoryMapper;
import com.thymeleaf.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description 项目地址：http://localhost:8085/thymeleaf/listCategory
 *
 */
@Controller
public class CategoryController {
    @Autowired(required = false)
    CategoryMapper categoryMapper;

    /**
    * 查询
    */
    @GetMapping("/listCategory")
    public String listCategory(Model m, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        PageHelper.startPage(start, size, "id desc");
        List<Category> cs = categoryMapper.findAll();
        PageInfo<Category> page = new PageInfo<>(cs);
        m.addAttribute("page", page);
        return "listCategory";
    }

    @PostMapping("/addCategory")
    public String listCategory(Category c) {
        categoryMapper.save(c);
        return "redirect:listCategory";
    }

    @GetMapping("/deleteCategory")
    public String deleteCategory(Category c) {
        categoryMapper.delete(c.getId());
        return "redirect:listCategory";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(Category c) {
        categoryMapper.update(c);
        return "redirect:listCategory";
    }

    @GetMapping("/editCategory")
    public String listCategory(int id, Model m) {
        Category c = categoryMapper.get(id);
        m.addAttribute("c", c);
        return "editCategory";
    }

}