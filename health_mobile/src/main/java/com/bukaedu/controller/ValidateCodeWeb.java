package com.bukaedu.controller;

import constant.MessageConstant;
import constant.RedisMessageConstant;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.CheckGroup;
import redis.clients.jedis.JedisPool;
import utils.SMSUtils;
import utils.ValidateCodeUtils;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName ValidateCodeWeb
 * @date 2022/8/16 09:49
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeWeb {

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result edit(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(4);

        try {

            SMSUtils.sendShortMessage("SMS_217915240",telephone,code.toString());

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,3*60,code.toString());

        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
