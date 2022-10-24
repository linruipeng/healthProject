package com.bukaedu.controller;

import constant.MessageConstant;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName UserWeb
 * @date 2022/8/22 09:27
 */
@RestController
@RequestMapping("/user")
public class UserWeb {
    @RequestMapping("/getUsername")
    public Result getUsername(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user!=null){
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }
        return new Result(false,MessageConstant.GET_USERNAME_FAIL);
    }
}
