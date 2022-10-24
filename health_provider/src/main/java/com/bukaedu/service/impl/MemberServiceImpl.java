package com.bukaedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bukaedu.dao.Memberdao;
import com.bukaedu.service.MemberService;
import freemarker.template.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName MemberServiceImpl
 * @date 2022/8/22 10:16
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    Memberdao memberdao;

    @Override
    public List<Integer> findMemberByMonth(List<String> month) {
        ArrayList<Integer> memberCount = new ArrayList<>();
        for (String s : month) {
//            s=s+".31";
//            Integer count=memberdao.findMemberByMonth(s);
//            memberCount.add(count);
            try {
                String date = s + "-1";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                calendar.setTime(sdf.parse(date));
                calendar.add(Calendar.MONTH,1);
                calendar.set(Calendar.DAY_OF_MONTH,0);
                String lastDayOfMonth = sdf.format(calendar.getTime());
                Integer count=memberdao.findMemberByMonth(lastDayOfMonth);
                memberCount.add(count);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return memberCount;
    }
}
