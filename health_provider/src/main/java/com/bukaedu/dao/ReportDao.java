package com.bukaedu.dao;

import java.util.List;
import java.util.Map;

public interface ReportDao {
    Integer findNewMember(String reportDate);

    Integer findTotalMember();

    Integer findWeekNewMember(String thisWeekMonday);

    Integer findMonthNewMember(String date);

    Integer findTodayOrderMember(String reportDate);

    Integer findWeekOrderMember(String thisWeekMonday);

    Integer findMonthOrderMember(String firstDay4ThisMonth);

    Integer findTodayVisitsMember(String reportDate);

    Integer findWeekVisitsMember(String thisWeekMonday);

    Integer findonthVisitsMember(String firstDay4ThisMonth);

    List<Map> findHotmeal();
}
