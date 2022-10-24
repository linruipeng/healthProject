package com.bukaedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bukaedu.service.CheckgroupService;
import constant.MessageConstant;
import entity.PageResult;
import entity.QueryPageBean;
import entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.CheckGroup;
import pojo.CheckItem;

import java.util.List;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName CheckgroupWeb
 * @date 2022/8/11 21:37
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupWeb {

    @Reference
    CheckgroupService checkgroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkgroupService.add(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=checkgroupService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkgroupService.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckGroup checkGroup=checkgroupService.findById(id);
        if (checkGroup!=null){
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }
        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findItemIdsByGroupId(Integer id){
        try {
            List<Integer> checkItemIds=checkgroupService.findItemIdsByGroupId(id);
            System.out.println(checkItemIds+"--------------");
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkgroupService.edit(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> checkGroupList=checkgroupService.findAll();
        if (checkGroupList!=null && checkGroupList.size()>0){
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }



}

