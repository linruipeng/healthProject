package com.bukaedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bukaedu.dao.CheckGroupDao;
import com.bukaedu.service.CheckgroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import entity.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.CheckGroup;
import pojo.CheckItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName CheckGroupServiceImpl
 * @date 2022/8/11 21:42
 */
@Service(interfaceClass = CheckgroupService.class)
public class CheckGroupServiceImpl implements CheckgroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        setCheckItemToGroup(checkGroup.getId(),checkitemIds);

    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> page=checkGroupDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void delete(Integer id) {
        long count=checkGroupDao.findCountByCheckItemId(id);
        if (count>0){
//            检查项被使用了，不能删
            throw new RuntimeException("当前检查组被引用，不可删除");
        }
        checkGroupDao.delete(id);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findItemIdsByGroupId(Integer id) {
        return checkGroupDao.findItemIdsByGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {

        //删除中间表id
        checkGroupDao.deleteItemIdsByGroup(checkGroup.getId());
//          关联新的id
        setCheckItemToGroup(checkGroup.getId(),checkitemIds);

        checkGroupDao.edit(checkGroup);

    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


    private void setCheckItemToGroup(Integer id, Integer[] checkitemIds) {
        if (checkitemIds!=null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map=new HashMap<>();
                map.put("checkgroup_id",id);
                map.put("checkitem_id",checkitemId);
                checkGroupDao.setCheckItemToGroup(map);

            }
        }
    }

}
