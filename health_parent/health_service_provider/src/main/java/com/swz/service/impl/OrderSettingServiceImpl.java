package com.swz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.swz.dao.OrderSettingDao;
import com.swz.pojo.OrderSetting;
import com.swz.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.util.Date;
import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月08日 10:52
 * @Description: TODO:
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void add (List<OrderSetting> orderSettings){
        for (OrderSetting orderSetting : orderSettings) {
            //查询是否存在
            long l = orderSettingDao.find(orderSetting.getOrderDate());
            if (l <= 0) {
                orderSettingDao.add(orderSetting);
            } else {
                orderSettingDao.update(orderSetting);
            }
        }
    }

    @Override
    public List<OrderSetting> findDateInfo (Date date){
        List<OrderSetting> orderSettings = orderSettingDao.findDateInfo(date);
        return orderSettings;
    }

    @Override
    public void feditNumberByDate (OrderSetting orderSetting){
        long l = orderSettingDao.find(orderSetting.getOrderDate());
        if (l<=0){
            //没有这个日期的数据就选择插入
            orderSettingDao.add(orderSetting);
        }else {
            //有就跟新
            orderSettingDao.update(orderSetting);
        }

    }
}
