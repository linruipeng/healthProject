package com.bukaedu.service;

import entity.PageResult;
import entity.QueryPageBean;
import pojo.CheckGroup;
import pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, Object>> finSetmealCount();
}
