package com.bukaedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bukaedu.service.OrderSettingService;
import constant.MessageConstant;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pojo.CheckGroup;
import pojo.OrderSetting;
import utils.POIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName Ordersettingweb
 * @date 2022/8/13 15:44
 */
@RestController
@RequestMapping("/ordersetting")
public class Ordersettingweb {
    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (list!=null && list.size()>0){
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] row : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(row[0]), Integer.parseInt(row[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.add(orderSettingList);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {

           List<Map> orderSetting=orderSettingService.getOrderSettingByMonth(date);

            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,orderSetting);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {

            orderSettingService.editNumberByDate(orderSetting);

            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }


}
