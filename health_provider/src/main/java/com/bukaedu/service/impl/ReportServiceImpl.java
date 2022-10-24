package com.bukaedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bukaedu.dao.ReportDao;
import com.bukaedu.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import utils.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName ReportServiceImpl
 * @date 2022/8/22 13:43
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportDao reportDao;
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
//        报表当天日期
        String reportDate = DateUtils.parseDate2String(DateUtils.getToday());
//        获取一周的信息
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
//        获取一个月的信息
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
//        新增会员数
        Integer todayNewMember=reportDao.findNewMember(reportDate);
//        总会员
        Integer totalMember=reportDao.findTotalMember();
//        本周新增会员
        Integer weekNewMember=reportDao.findWeekNewMember(thisWeekMonday);
//        本月新增会员
        Integer monthNewMember=reportDao.findMonthNewMember(firstDay4ThisMonth);
//        今日预约数
        Integer todayOrderMember=reportDao.findTodayOrderMember(reportDate);
//        本周预约数
        Integer weekOrderMember=reportDao.findWeekOrderMember(thisWeekMonday);
//        本月预约数
        Integer monthOrderMember=reportDao.findMonthOrderMember(firstDay4ThisMonth);
//        今日到诊数
        Integer todayVisitsMember=reportDao.findTodayVisitsMember(reportDate);
//        本周到诊数
        Integer weekVisitsMember=reportDao.findWeekVisitsMember(thisWeekMonday);
//        本月到诊数
        Integer monthVisitsMember=reportDao.findonthVisitsMember(firstDay4ThisMonth);

//        热门套餐
        List<Map> hotMeal=reportDao.findHotmeal();
        HashMap<String, Object> result = new HashMap<>();
        result.put("reportDate",reportDate);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",weekNewMember);
        result.put("thisMonthNewMember",monthNewMember);
        result.put("todayOrderNumber",todayOrderMember);
        result.put("thisWeekOrderNumber",weekOrderMember);
        result.put("thisMonthOrderNumber",monthOrderMember);
        result.put("todayVisitsNumber",todayVisitsMember);
        result.put("thisWeekVisitsNumber",weekVisitsMember);
        result.put("thisMonthVisitsNumber",monthVisitsMember);
        result.put("hotSetmeal",hotMeal);

        return result;
    }
}
