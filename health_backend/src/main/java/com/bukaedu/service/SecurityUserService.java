package com.bukaedu.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pojo.Permission;
import pojo.Role;
import pojo.User;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName SecurityUserService
 * @date 2022/8/17 15:00
 */
@Component
public class SecurityUserService implements UserDetailsService {

    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userService.selectByName(username);
        if (user==null){
            return null;
        }
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        Set<Role> userRoles = user.getRoles();
        for (Role userRole : userRoles) {
            list.add(new SimpleGrantedAuthority(userRole.getKeyword()));
            Set<Permission> permissions = userRole.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        org.springframework.security.core.userdetails.User
                user1=new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return user1;
    }
}
