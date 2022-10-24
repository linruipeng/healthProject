package com.bukaedu.dao;

import pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    long getCountByDate(Date orderDate);

    void editByDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByDate(Map map);

    OrderSetting selectByDate(Date orderDate);

    void editReservations(OrderSetting orderSetting);
}
