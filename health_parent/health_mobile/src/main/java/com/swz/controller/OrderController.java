package com.swz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.swz.constant.MessageConstant;
import com.swz.constant.RedisMessageConstant;
import com.swz.entity.Result;
import com.swz.exception.OrderException;
import com.swz.pojo.Member;
import com.swz.pojo.Order;
import com.swz.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月11日 10:39
 * @Description: TODO:
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    JedisPool jedisPool;

    @Reference
    OrderService orderService;

    @RequestMapping("submit")
    public Result submit (@RequestBody Map map){
        String validateCode = (String) map.get("validateCode");
        String telephone = (String) map.get("telephone");
        if (validateCode != null && !validateCode.equals("")) {
            String redisValidateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
            //校验验证码是否正确
            if (validateCode.equals(redisValidateCode)) {
                //提交交到数据库
                try {
                    map.put("orderType", Order.ORDERTYPE_WEIXIN); //微信预约
                    Integer id = orderService.add(map);
                    //发送预约成功的短息
                    /**********调用短信服务发送短信**********/
                    return new Result(true, MessageConstant.ORDERSETTING_SUCCESS, id);
                } catch (OrderException e) {
                    e.printStackTrace();
                    return new Result(false, e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(false, MessageConstant.ORDERSETTING_FAIL);
                }
            }
        }
        return new Result(false, MessageConstant.ORDERSETTING_FAIL);
    }

    @RequestMapping("/findById")
    public Result findById (Integer id){
        try {
            Map<String,String> map= orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}