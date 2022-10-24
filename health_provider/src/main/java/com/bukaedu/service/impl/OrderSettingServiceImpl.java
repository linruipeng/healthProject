package com.bukaedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bukaedu.dao.OrderSettingDao;
import com.bukaedu.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.OrderSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName OrderSettingServiceImpl
 * @date 2022/8/13 16:13
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList!=null && orderSettingList.size()>0){
            for (OrderSetting orderSetting : orderSettingList) {
//                判断日期是否存在
                long count=orderSettingDao.getCountByDate(orderSetting.getOrderDate());
                if (count>0){
//                    日期已经存在，修改预约人数
                    orderSettingDao.editByDate(orderSetting);
                }else {
                    orderSettingDao.add(orderSetting);
                }
            }

        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {

        String beginDate=date+"-1";
        String endDate=date+"-31";
        Map map=new HashMap<>();
        map.put("beginDate",beginDate);
        map.put("endDate",endDate);
        List<OrderSetting> orderSettingList=orderSettingDao.getOrderSettingByDate(map);//区间查找
        List<Map> show_orderSetting=new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {

//            把区间内查到的orderSetting集合重新命名封装到map中返回
            Map get_orderSetting=new HashMap();
            get_orderSetting.put("date",orderSetting.getOrderDate().getDate());
            get_orderSetting.put("number",orderSetting.getNumber());
            get_orderSetting.put("reservations",orderSetting.getReservations());

//            封装到show_orderSetting  返回给前端
            show_orderSetting.add(get_orderSetting);
        }
        return show_orderSetting;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.getCountByDate(orderSetting.getOrderDate());
        if (count>0){
            orderSettingDao.editByDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
