package com.plus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.plus.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-24
 */
public interface UserService extends IService<User> {

    PageInfo<User> selectAll(int pageNum, int pageSize, User user);

    int add(User user);

    int update(User user);

    int delete(int id);

    /**
    * 使用mybatis-plus分页插件
    */
    IPage<User> select(Page<User> page);
}
