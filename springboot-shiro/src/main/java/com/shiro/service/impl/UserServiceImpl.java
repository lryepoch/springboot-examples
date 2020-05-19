package com.shiro.service.impl;

import com.shiro.entity.User;
import com.shiro.entity.UserExample;
import com.shiro.mapper.UserMapper;
import com.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/5/19 17:16
 * @description TODO
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

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
}
