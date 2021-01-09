package com.jsp.service;

import com.jsp.entity.User;

/**
 * @author lryepoch
 * @date 2020/5/19 17:16
 * @description TODO
 */
public interface UserService {

    User findByUsername(String name);
}
