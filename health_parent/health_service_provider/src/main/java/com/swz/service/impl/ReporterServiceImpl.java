package com.swz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.swz.dao.ReporterDao;
import com.swz.service.ReporterService;
import com.swz.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 13:39
 * @Description: TODO:
 */

@Service(interfaceClass = ReporterService.class)
@Transactional
public class ReporterServiceImpl implements ReporterService {
    @Autowired
    ReporterDao reporterDao;

    @Override
    public Map<String, Object> getMemberReport (){
        Map<String, Object> map = new HashMap<>();
        List<String> listMounth = new ArrayList<>(); //记录月份
        List<Integer> dataList = new ArrayList<>(); //记录数据
        Calendar calendar = Calendar.getInstance(); //获得当前时间的日历
        calendar.add(Calendar.MONTH, -12);
        //拿到过去12个月的月份
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            String ym = new SimpleDateFormat("yyyy.MM").format(calendar.getTime());
            listMounth.add(ym);
            int day = calendar.getActualMaximum(Calendar.DATE);//当月的天数
            String date = ym + day; //查询每个月的数据
            Integer count = reporterDao.getMemberReport(date);
            dataList.add(count);
        }
        map.put("months", listMounth);
        map.put("memberCount", dataList);
        return map;
    }

    @Override
    public Map<String, Object> getSetmealReport (){
        //去数据库查询套餐的数量(名称、数量)
        List<Map<String, Object>> setMeals = reporterDao.getSetmealReport();
        //记录套餐的名称
        List<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> setMeal : setMeals) {
            String name = (String) setMeal.get("name");
            setmealNames.add(name);
        }
        //将名称，数量封装到map中
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("setmealNames", setmealNames);
        dataMap.put("setmealCount", setMeals);
        return dataMap;
    }
    /*
                        reportDate:null,
                        todayNewMember :0,
                        totalMember :0,
                        thisWeekNewMember :0,
                        thisMonthNewMember :0,
                        todayOrderNumber :0,

                        todayVisitsNumber :0,
                        thisWeekOrderNumber :0,
                        thisWeekVisitsNumber :0,
                        thisMonthOrderNumber :0,
                        thisMonthVisitsNumber :0,
                         hotSetmeal :[
                        {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
                        {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
                    ]
     */
    //获得会员统计数据
    @Override
    public Map<String, Object> getBusinessReportData (){
        Map<String, Object> reportData = new HashMap<>();
        //今日日期
        Date today = new Date();
        //获得本周周一的日期
        Date thisWeekMonday = DateUtils.getThisWeekMonday();
        //获得本月一号的日期
        Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();


        //今日新增会员数
        Integer todayNewMember = reporterDao.getTodayNewMember(today);
        //所有会员的数量
        Integer totalMember = reporterDao.getTotalMember();
        //这周的会员数量
        Integer thisWeekNewMember = reporterDao.getThisWeekNewMember(today, thisWeekMonday);
        //这个月的会员数量
        Integer thisMonthNewMember = reporterDao.getThisMonthNewMember(today, firstDay4ThisMonth);
        //今日订单的数量
        Integer todayOrderNumber = reporterDao.getTodayOrderNumber(today);
        //今日到诊的数量
        Integer todayVisitsNumber = reporterDao.todayVisitsNumber(today);
        //本周的订单数量
        Integer thisWeekOrderNumber = reporterDao.getThisWeekOrderNumber(today, thisWeekMonday);
        //本周到诊数量
        Integer thisWeekVisitsNumber = reporterDao.getThisWeekVisitsNumber(today, thisWeekMonday);
        //本月订单数量
        Integer thisMonthOrderNumber = reporterDao.getThisMonthOrderNumber(today, firstDay4ThisMonth);
        //本月到诊数量
        Integer thisMonthVisitsNumber = reporterDao.getThisMonthVisitsNumber(today, firstDay4ThisMonth);
        //热门套餐
        List<Map<String, Object>> hotSetmeal = reporterDao.getHotSetmeal(today, firstDay4ThisMonth);

        //设置本月受欢迎度
        for (Map<String, Object> map : hotSetmeal) {
            long setmeal_count = (long) map.get("setmeal_count");
            //本套餐的受欢迎度
            BigDecimal bigSetmeal_count = new BigDecimal(setmeal_count);
            BigDecimal bigThisMonthOrderNumber = new BigDecimal(thisMonthOrderNumber);
            BigDecimal res = bigSetmeal_count.divide(bigSetmeal_count, 2, BigDecimal.ROUND_HALF_DOWN);
            map.put("proportion",res);
        }

        //报表日期
        reportData.put("reportDate", today);
        reportData.put("todayNewMember", todayNewMember);
        reportData.put("totalMember", totalMember);
        reportData.put("thisWeekNewMember", thisWeekNewMember);
        reportData.put("thisMonthNewMember", thisMonthNewMember);
        reportData.put("todayOrderNumber", todayOrderNumber);
        reportData.put("thisWeekOrderNumber", thisWeekOrderNumber);
        reportData.put("thisMonthOrderNumber", thisMonthOrderNumber);
        reportData.put("todayVisitsNumber", todayVisitsNumber);
        reportData.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        reportData.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        reportData.put("hotSetmeal", hotSetmeal);
        return reportData;
    }


}
