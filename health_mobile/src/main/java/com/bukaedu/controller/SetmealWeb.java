package com.bukaedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bukaedu.service.SetmealService;
import constant.MessageConstant;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Setmeal;

import java.util.List;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName SetmealWeb
 * @date 2022/8/15 10:45
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealWeb {
    @Reference
    SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result findAll(){
        List<Setmeal> list=setmealService.findAll();
        if (list!=null && list.size()>0){
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
        }
        return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal=setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

}
