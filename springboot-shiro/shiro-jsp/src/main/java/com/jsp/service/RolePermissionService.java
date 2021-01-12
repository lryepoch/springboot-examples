package com.jsp.service;

import com.jsp.entity.Role;

public interface RolePermissionService {
	void setPermissions(Role role, long[] permissionIds);

	void deleteByRole(long roleId);

	void deleteByPermission(long permissionId);
}