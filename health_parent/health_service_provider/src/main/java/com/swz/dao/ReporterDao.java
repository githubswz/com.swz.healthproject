package com.swz.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 13:49
 * @Description: TODO:
 */
public interface ReporterDao {
    //查询当月的所有会员数量
    Integer getMemberReport (String date);

    List<Map<String, Object>> getSetmealReport ();

    Integer getTodayNewMember (Date today);

    Integer getTotalMember ();

    Integer getThisWeekNewMember (@Param("today") Date today, @Param("thisWeekMonday") Date thisWeekMonday);

    Integer getThisMonthNewMember (@Param("today") Date today, @Param("firstDay4ThisMonth") Date firstDay4ThisMonth);

    Integer getTodayOrderNumber (Date today);

    Integer todayVisitsNumber (Date today);

    Integer getThisWeekOrderNumber (@Param("today") Date today, @Param("thisWeekMonday") Date thisWeekMonday);

    Integer getThisWeekVisitsNumber (@Param("today") Date today, @Param("thisWeekMonday") Date thisWeekMonday);

    Integer getThisMonthOrderNumber (@Param("today") Date today, @Param("firstDay4ThisMonth") Date firstDay4ThisMonth);

    Integer getThisMonthVisitsNumber (@Param("today") Date today, @Param("firstDay4ThisMonth") Date firstDay4ThisMonth);

    List<Map<String, Object>> getHotSetmeal (@Param("today") Date today, @Param("firstDay4ThisMonth") Date firstDay4ThisMonth);
}
