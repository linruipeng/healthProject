package com.bukaedu.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import pojo.CheckGroup;
import pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckItemToGroup(Map<String, Integer> map);

    Page<CheckGroup> selectByCondition(@Param("queryString") String queryString);

    long findCountByCheckItemId(Integer id);

    void delete(Integer id);

    CheckGroup findById(Integer id);

    List<Integer> findItemIdsByGroupId(Integer id);

    void deleteItemIdsByGroup(Integer id);

    void edit(CheckGroup checkGroup);

    List<CheckGroup> findAll();
}
