package com.bukaedu.dao;

import pojo.CheckGroup;
import pojo.TSetmealCheckgroupExample;
import pojo.TSetmealCheckgroupKey;

import java.util.List;

public interface TCheckGroupMapper {
    List<TSetmealCheckgroupKey> selectByExample(TSetmealCheckgroupExample example);

    CheckGroup selectById(Integer id);
}
