package com.plus.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.plus.entity.User;
import com.plus.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-24
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping(value = "/selectAll")
    public Object selectAll(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "5") int pageSize,
                            User user) {
        PageInfo<User> pageInfo = userService.selectAll(pageNum, pageSize, user);
        return pageInfo;
    }

    /**
    * mybatis-plus分页插件
    */
    @GetMapping(value = "/select")
    public Object select() {
        IPage<User> pageInfo = userService.select(new Page<>(1, 5));
        return pageInfo;
    }

    @PostMapping(value = "/add")
    public int add(User user) {
        return userService.add(user);
    }

    @PostMapping(value = "/update")
    public int update(User user){
        return userService.update(user);
    }

    @DeleteMapping(value = "delete/{id}")
    public int delete(@PathVariable("id") Integer id){
        return userService.delete(id);
    }

}

