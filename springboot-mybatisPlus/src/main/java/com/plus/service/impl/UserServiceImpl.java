package com.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.plus.entity.User;
import com.plus.dao.UserMapper;
import com.plus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lryepoch
 * @since 2020-12-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public PageInfo<User> selectAll(int pageNum, int pageSize, User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (user.getId()!=null){
            queryWrapper.eq("id", user.getId());
        }
        queryWrapper.orderBy(true, false, "id");
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectList(queryWrapper);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int add(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int delete(int id) {
        return userMapper.deleteById(id);
    }

    /**
     * 使用mybatis-plus分页插件
     *
     * @param page
     */
    @Override
    public IPage<User> select(Page<User> page) {
        return userMapper.selectPage(page);
    }


}
