package com.jsp.service.impl;

import com.jsp.entity.User;
import com.jsp.entity.UserExample;
import com.jsp.mapper.UserMapper;
import com.jsp.service.UserRoleService;
import com.jsp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/5/19 17:16
 * @description TODO
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleService userRoleService;

    @Override
    public User findByUsername(String name) {
        UserExample userExample = new UserExample();
        userExample.or().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);
    }

    @Override
    public User get(long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(long id) {
        userMapper.deleteByPrimaryKey(id);
        userRoleService.deleteByUser(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }
}
