package com.bukaedu.dao;

import com.github.pagehelper.Page;
import entity.PageResult;
import entity.QueryPageBean;
import org.apache.ibatis.annotations.Param;
import pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);

    void edit(CheckItem checkItem);

    Page<CheckItem> selectByCondition(@Param("queryString") String queryString);

    void delete(Integer id);

    long findCountByCheckItemId(Integer id);

    CheckItem findById(Integer id);

    List<CheckItem> findAll();
}
