package com.bukaedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bukaedu.service.OrderService;
import constant.MessageConstant;
import constant.RedisMessageConstant;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Order;
import redis.clients.jedis.JedisPool;
import utils.SMSUtils;

import java.util.Map;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName OrderWeb
 * @date 2022/8/16 10:40
 */
@RestController
@RequestMapping("/order")
public class OrderWeb {

    @Autowired
    JedisPool jedisPool;
    @Reference
    OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");

        if (!code.equals(validateCode) || validateCode==null){
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
        Result result = null;
        try {
            map.put("ordertype", Order.ORDERTYPE_WEIXIN);
            result=orderService.setOrder(map);
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }

        if (result.isFlag()){
            String orderdate = (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderdate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {

            Map map=orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
