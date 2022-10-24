package com.bukaedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bukaedu.dao.SetmealDao;
import com.bukaedu.dao.TCheckGroupMapper;
import com.bukaedu.dao.TCheckItemsMapper;
import com.bukaedu.dao.TSetmealMapper;
import com.bukaedu.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import constant.RedisConstant;
import entity.PageResult;
import entity.QueryPageBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import pojo.*;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.*;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName SetmealServiceImpl
 * @date 2022/8/12 16:30
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService{

    @Autowired
    SetmealDao setmealDao;
    @Autowired
    JedisPool jedisPool;
    @Autowired
    TSetmealMapper tSetmealMapper;
    @Autowired
    TCheckGroupMapper tCheckGroupMapper;
    @Autowired
    TCheckItemsMapper tCheckItemsMapper;
    @Autowired
    FreeMarkerConfigurer freeMarkerConfig;
    @Value("${out_put_path}")
    String outputPath;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        setmealIdAndgroupId(setmeal.getId(),checkgroupIds);
        setmealIdToRedis(setmeal.getImg());
        genMobStaticHtml();

    }


//    生成静态页面
    public void genMobStaticHtml(){
        List<Setmeal> setmealList = this.findAll();
//        给套餐生成静态页面
        generateMobSetmealHtml(setmealList);
//        给每个套餐详情生成静态页面
        generateMobSetmealDetailHtml(setmealList);
    }

    private void generateMobSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map map = new HashMap<>();
            map.put("setmeal",this.findById(setmeal.getId()));
            this.generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);
        }
    }


    private void generateMobSetmealHtml(List<Setmeal> setmealList) {
        Map map = new HashMap<>();
        map.put("setmealList",setmealList);
        this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }

    private void generateHtml(String templateName, String htmlPageName, Map map) {
        Configuration configuration = freeMarkerConfig.getConfiguration();
        configuration.setClassicCompatible(true);
        Writer out=null;
        try {
            Template template = configuration.getTemplate(templateName);
            File file = new File(outputPath + "/" + htmlPageName);
            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            template.process(map,out);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page=setmealDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> list=setmealDao.findAll();
        return list;
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal=tSetmealMapper.findById(id);
//
        TSetmealCheckgroupExample example = new TSetmealCheckgroupExample();
//        连接数据库查询（工具类）  根据id查询实体类对象
        example.createCriteria().andSetmealIdEqualTo(id);

        List<TSetmealCheckgroupKey> tSetmealCheckgroupKeys=tCheckGroupMapper.selectByExample(example);
        ArrayList<CheckGroup> checkGroups = new ArrayList<>();
        for (TSetmealCheckgroupKey tSetmealCheckgroupKey : tSetmealCheckgroupKeys) {
            CheckGroup checkGroup=tCheckGroupMapper.selectById(tSetmealCheckgroupKey.getCheckgroupId());
            checkGroups.add(checkGroup);
        }

        for (CheckGroup checkGroup : checkGroups) {
            ArrayList<CheckItem> checkItems = new ArrayList<>();
            TCheckgroupCheckitemExample example1 = new TCheckgroupCheckitemExample();
            example1.createCriteria().andCheckgroupIdEqualTo(checkGroup.getId());
            List<TCheckgroupCheckitemKey> tCheckgroupCheckitemKeys=tCheckItemsMapper.selectByExample(example1);
            for (TCheckgroupCheckitemKey tCheckgroupCheckitemKey : tCheckgroupCheckitemKeys) {
                CheckItem checkItem=tCheckItemsMapper.selectById(tCheckgroupCheckitemKey.getCheckitemId());
                checkItems.add(checkItem);
            }
            checkGroup.setCheckItems(checkItems);
        }
        setmeal.setCheckGroups(checkGroups);

        return setmeal;
    }

    @Override
    public List<Map<String, Object>> finSetmealCount() {
        return setmealDao.finSetmealCount();
    }

    private void setmealIdToRedis(String img) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
    }


    private void setmealIdAndgroupId(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put("setmeal_id",id);
            hashMap.put("checkgroup_id",checkgroupId);
            setmealDao.setmealIdAndgroupId(hashMap);
        }

    }

}
