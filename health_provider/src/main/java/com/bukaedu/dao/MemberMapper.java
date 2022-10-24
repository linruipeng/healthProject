package com.bukaedu.dao;

import pojo.Member;

public interface MemberMapper {
    Member selectByTel(String telephone);

    void add(Member new_member);
}
