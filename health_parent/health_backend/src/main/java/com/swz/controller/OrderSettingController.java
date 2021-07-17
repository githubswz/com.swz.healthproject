package com.swz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.swz.constant.MessageConstant;
import com.swz.entity.Result;
import com.swz.pojo.OrderSetting;
import com.swz.service.OrderSettingService;
import com.swz.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author : 苏文致
 * @date Date : 2021年07月08日 10:14
 * @Description: TODO:
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload (@RequestParam("excelFile") MultipartFile excelFile){
        List<String[]> list = null;
        try {
            list = POIUtils.readExcel(excelFile);
            if (list != null && list.size() > 0) {
                List<OrderSetting> orderSettings = new ArrayList<>();
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    orderSettings.add(orderSetting);
                }
                orderSettingService.add(orderSettings);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/findDateInfo")
    public Result findDateInfo (String date){
        try {
            Date day = new Date(date);
            List<OrderSetting> orderSettings = orderSettingService.findDateInfo(day);
           List<Map<String,Integer>> maps = new ArrayList<>();
            for (OrderSetting orderSetting : orderSettings) {
                Map<String,Integer> map = new HashMap<>();
                int date1 = orderSetting.getOrderDate().getDate();
                map.put("date",date1);
                map.put("number",orderSetting.getNumber());
                map.put("reservations",orderSetting.getReservations());
                maps.add(map);
            }
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, maps);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result feditNumberByDate (@RequestBody  OrderSetting orderSetting){
        try {
                orderSettingService.feditNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
