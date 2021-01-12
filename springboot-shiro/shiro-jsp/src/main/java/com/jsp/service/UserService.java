package com.jsp.service;

import com.jsp.entity.User;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/5/19 17:16
 * @description TODO
 */
public interface UserService {

    User findByUsername(String name);

    List<User> list();

    User get(long id);

    void delete(long id);

    void update(User user);

    void add(User u);
}
