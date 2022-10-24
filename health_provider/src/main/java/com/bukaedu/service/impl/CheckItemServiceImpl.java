package com.bukaedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bukaedu.dao.CheckItemDao;
import com.bukaedu.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import entity.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.CheckItem;

import java.util.List;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName CheckItemServiceImpl
 * @date 2022/8/10 15:05
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService{

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        System.out.println(queryPageBean.getQueryString()+"-----------------");
        Page<CheckItem> page=checkItemDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) {
        long count=checkItemDao.findCountByCheckItemId(id);
        if (count>0){
//            检查项被使用了，不能删
            throw new RuntimeException("当前检查项被引用，不可删除");
        }
        checkItemDao.delete(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem=checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
