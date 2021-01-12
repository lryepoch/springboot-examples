package com.jsp.service;


import com.jsp.entity.User;

public interface UserRoleService {

	void setRoles(User user, long[] roleIds);

	void deleteByUser(long userId);

	void deleteByRole(long roleId);

}