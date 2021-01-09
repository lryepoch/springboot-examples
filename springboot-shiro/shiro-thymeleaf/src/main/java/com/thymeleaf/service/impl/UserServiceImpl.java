package com.thymeleaf.service.impl;

import com.thymeleaf.service.UserService;
import com.thymeleaf.entity.User;
import com.thymeleaf.entity.UserExample;
import com.thymeleaf.mapper.UserMapper;
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
