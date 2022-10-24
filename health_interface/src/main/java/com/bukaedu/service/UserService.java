package com.bukaedu.service;

import pojo.User;

public interface UserService {
    User selectByName(String username);
}
