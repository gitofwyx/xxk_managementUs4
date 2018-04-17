package com.xxk.management.roles.service;

import com.xxk.management.roles.entity.Roles;

import java.util.List;


public interface RolesService {

    boolean addUserRole(Roles role);

    List<String> getUserRoles();

    String getUserRoleId(String roleVal);
}
