package com.bukaedu.dao;

import pojo.CheckItem;
import pojo.TCheckgroupCheckitemExample;
import pojo.TCheckgroupCheckitemKey;

import java.util.List;

public interface TCheckItemsMapper {
    List<TCheckgroupCheckitemKey> selectByExample(TCheckgroupCheckitemExample example1);

    CheckItem selectById(Integer id);
}
