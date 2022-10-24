package com.bukaedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bukaedu.dao.MemberMapper;
import com.bukaedu.dao.OrderDao;
import com.bukaedu.dao.OrderSettingDao;
import com.bukaedu.service.OrderService;
import constant.MessageConstant;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.Member;
import pojo.Order;
import pojo.OrderSetting;
import utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 1.检查用户所选择的日期是否提前进行了预约设置,如果没有预约设置则进行预约
 * 2.检查用户所选的日期是否已经预约满了,如果满了  预约不了
 * 3.检查用户是否重复预约(同一个用户,同一天,同一个套餐),如果重复预约则无法完成预约
 * 4.检查当前用户是否是会员,如果是会员则直接预约,如果不是会员,需要自动将用户信息添加到会员表中
 * 5.预约成功的时候,更新当日预约人数
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderSettingDao orderSettingDao;
    @Autowired
    MemberMapper memberMapper;

    @Override
    public Result setOrder(Map map) throws Exception {
//         * 1.检查用户所选择的日期是否提前进行了预约设置,如果没有预约设置则进行预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting=orderSettingDao.selectByDate(date);

        if (orderSetting==null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
//         * 2.检查用户所选的日期是否已经预约满了,如果满了  预约不了
        if (orderSetting.getReservations()>=orderSetting.getNumber()){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

//         * 3.检查用户是否重复预约(同一个用户,同一天,同一个套餐),如果重复预约则无法完成预约
//         * 4.检查当前用户是否是会员,如果是会员则直接预约,如果不是会员,需要自动将用户信息添加到会员表中
        String telephone = (String) map.get("telephone");
        Member member=memberMapper.selectByTel(telephone);
//        是会员
        if (member!=null){
            Integer id = member.getId();
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(id, date, null, null, setmealId);
            List<Order> list=orderDao.findByCondition(order);
            if (list!=null&&list.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }else {
                orderSetting.setReservations(orderSetting.getReservations()+1);
            orderSetting.setNumber(orderSetting.getNumber()- orderSetting.getReservations());
                orderSettingDao.editReservations(orderSetting);

            }
        }else{
            orderSetting.setReservations(orderSetting.getReservations()+1);
            orderSetting.setNumber(orderSetting.getNumber()- orderSetting.getReservations());
            orderSettingDao.editReservations(orderSetting);

            //          不是会员
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());

            memberMapper.add(member);

        }

//        orderSetting.setReservations(orderSetting.getReservations()+1);
////            orderSetting.setNumber(orderSetting.getNumber()- orderSetting.getReservations());
//        orderSettingDao.editReservations(orderSetting);

        Order order = new Order(member.getId(), date, (String) map.get("ordertype"),
                Order.ORDERSTATUS_NO, Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());

    }

    @Override
    public Map findById(Integer id) throws Exception {
        Map map=orderDao.findById(id);
        if (map!=null){
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }
}
