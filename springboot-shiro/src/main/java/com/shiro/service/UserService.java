package com.shiro.service;

import com.shiro.entity.User;

/**
 * @author lryepoch
 * @date 2020/5/19 17:16
 * @description TODO
 */
public interface UserService {

    User findByUsername(String name);
}
