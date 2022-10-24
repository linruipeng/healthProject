package com.bukaedu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName Sectiry
 * @date 2022/8/17 13:47
 */

public class Sectiry implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    Map<String, User> userMap=new HashMap<>();

    public void initUserData(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        User user1 = new User();
        user1.setUsername("lili");
        user1.setPassword(bCryptPasswordEncoder.encode("1234"));
        userMap.put(user.getUsername(),user);
        userMap.put(user1.getUsername(),user1);

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        initUserData();
        User user = userMap.get(s);
        if (user==null){
            return null;
        }
        String password = user.getPassword();
        ArrayList<GrantedAuthority> list = new ArrayList<>();
//        list.add(new SimpleGrantedAuthority("add"));
        list.add(new SimpleGrantedAuthority("delete"));
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        org.springframework.security.core.userdetails.User user1=new org.springframework.security.core.userdetails.User(s,password,list);

        return user1;
    }
}
