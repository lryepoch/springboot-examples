package com.springboot.jpa.controller;
import com.springboot.jpa.dao.CategoryDAO;
import com.springboot.jpa.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lryepoch
 * @date 2019-11-29 2019/11/29
 * @description
 */
@RestController
public class CategoryController {
    @Autowired
    CategoryDAO categoryDAO;

    @RequestMapping("/listCategory")
    public Page<Category> listCategory(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "3") int size) throws Exception{
        start = start < 0 ? 0 : start;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Category> page = categoryDAO.findAll(pageable);
        return page;
    }

    @RequestMapping("/addCategory")
    public Category addCategory(@RequestBody Category c) throws Exception{
        return categoryDAO.save(c);
    }

    @RequestMapping("/updateCategory")
    public Category updateCategory(@RequestBody Category c) throws Exception{
        return categoryDAO.save(c);
    }

    @RequestMapping("/deleteCategory")
    public String deleteCategory(@RequestBody Category c) throws Exception{
        categoryDAO.delete(c);
        return "删除成功";
    }
}