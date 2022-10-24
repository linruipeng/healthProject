package com.bukaedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bukaedu.dao.UserDao;
import com.bukaedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.Permission;
import pojo.Role;
import pojo.User;

import java.util.Set;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName UserServiceImpl
 * @date 2022/8/17 15:04
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User selectByName(String username) {
        User user=userDao.findUserByName(username);
        if (user==null){
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles=userDao.findRolesByUserId(userId);
        if (roles!=null && roles.size()>0){
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permissions=userDao.findPermissionByRoleId(roleId);
                if (permissions!=null && permissions.size()>0){
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }

        return user;
    }
}
