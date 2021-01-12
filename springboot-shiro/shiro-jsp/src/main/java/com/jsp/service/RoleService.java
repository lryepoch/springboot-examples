package com.jsp.service;

import com.jsp.entity.Role;
import com.jsp.entity.User;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/5/19 17:44
 * @description TODO
 */
public interface RoleService {
    List<String> listRoleNames(String name);

    List<Role> list();

    Role get(long id);

    void update(Role role);

    void add(Role role);

    void delete(long id);

    List<Role> listRoles(User user);
}
