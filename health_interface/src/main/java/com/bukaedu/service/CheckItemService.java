package com.bukaedu.service;

import entity.PageResult;
import entity.QueryPageBean;
import pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    void add(CheckItem checkItem);

    void edit(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    CheckItem findById(Integer id);

    List<CheckItem> findAll();
}
