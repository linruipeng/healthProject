package com.bukaedu.service;

import entity.PageResult;
import entity.QueryPageBean;
import pojo.CheckGroup;
import pojo.CheckItem;

import java.util.List;

public interface CheckgroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    CheckGroup findById(Integer id);

    List<Integer> findItemIdsByGroupId(Integer id);



    void edit(CheckGroup checkGroup,Integer[] checkitemIds);

    List<CheckGroup> findAll();
}
