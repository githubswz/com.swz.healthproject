package com.swz.dao;

import com.swz.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月08日 10:54
 * @Description: TODO:
 */
public interface OrderSettingDao {
    void add (OrderSetting orderSetting);

    long find (Date orderDate);

    void update (OrderSetting orderSetting);

    List<OrderSetting> findDateInfo (Date orderDate);

    void updateReservationById (@Param("date") Date date, @Param("reservation") Integer reservation);

    /**
     * 查询当日预约人数是否已满
     *
     * @param date
     * @return
     */
    Integer findReservationByDate (Date date);

    Integer findNumberByDate (Date date);
}
