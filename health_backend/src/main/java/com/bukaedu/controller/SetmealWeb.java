package com.bukaedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bukaedu.service.SetmealService;
import constant.MessageConstant;
import constant.RedisConstant;
import entity.PageResult;
import entity.QueryPageBean;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pojo.CheckGroup;
import pojo.Setmeal;
import redis.clients.jedis.JedisPool;
import utils.QiniuUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName SetmealWeb
 * @date 2022/8/12 16:11
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealWeb {
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
//            获取文件名称
            String filename = imgFile.getOriginalFilename();
            int lastIndexOf = filename.lastIndexOf(".");
            String suffix = filename.substring(lastIndexOf - 1);  //得到.jpg
//            生成图片名称
            String img_name = UUID.randomUUID().toString()+suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),img_name);

            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,img_name);

            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,img_name);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @Reference
    SetmealService setmealService;

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=setmealService.findPage(queryPageBean);
        return pageResult;
    }

}
