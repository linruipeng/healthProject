package com.bukaedu.dao;

import com.github.pagehelper.Page;
import pojo.CheckGroup;
import pojo.Setmeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void setmealIdAndgroupId(HashMap<String, Integer> hashMap);

    Page<Setmeal> selectByCondition(String queryString);

    List<Setmeal> findAll();

    List<Map<String, Object>> finSetmealCount();
}
