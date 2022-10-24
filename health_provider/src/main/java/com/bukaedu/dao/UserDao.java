package com.bukaedu.dao;

import pojo.Permission;
import pojo.Role;
import pojo.User;

import java.util.Set;

public interface UserDao {
    User findUserByName(String username);

    Set<Role> findRolesByUserId(Integer userId);

    Set<Permission> findPermissionByRoleId(Integer roleId);
}
