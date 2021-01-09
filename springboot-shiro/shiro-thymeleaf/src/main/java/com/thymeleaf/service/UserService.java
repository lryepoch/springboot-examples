package com.thymeleaf.service;

import com.thymeleaf.entity.User;

/**
 * @author lryepoch
 * @date 2020/5/19 17:16
 * @description TODO
 */
public interface UserService {

    User findByUsername(String name);
}
