package com.swz.service;

import com.swz.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月08日 10:51
 * @Description: TODO:
 */
public interface OrderSettingService {
    void  add(List<OrderSetting> orderSettings);

    List<OrderSetting> findDateInfo (Date date);

    void feditNumberByDate (OrderSetting orderSetting);
}
