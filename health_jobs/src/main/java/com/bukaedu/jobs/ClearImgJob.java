package com.bukaedu.jobs;

import constant.MessageConstant;
import constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import utils.QiniuUtils;

import java.util.Set;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName ClearImgJob
 * @date 2022/8/13 11:21
 */

public class ClearImgJob {
    @Autowired
    JedisPool jedisPool;
    public void clearImg(){
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set!=null){
            for (String s : set) {
                QiniuUtils.deleteFileFromQiniu(s);
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            }
        }
    }
}
