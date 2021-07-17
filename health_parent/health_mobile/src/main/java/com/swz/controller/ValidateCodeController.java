package com.swz.controller;

import com.aliyuncs.exceptions.ClientException;
import com.swz.constant.MessageConstant;
import com.swz.constant.RedisMessageConstant;
import com.swz.entity.Result;
import com.swz.utils.SMSUtils;
import com.swz.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author : 苏文致
 * @date Date : 2021年07月11日 9:49
 * @Description: TODO:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/send4Order")
    public Result send4Order (String telephone){
        //生成随机6位数的验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6); //生成6位数字验证码
        try {
            //将验证码通过阿里云发送给用户
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());

            System.out.println("验证码为【"+code+"】"); //由于未注册阿里云短信验证，在此模拟短信发送的验证码
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将数据存放到Redis中，并设置有效时间5分钟
        jedisPool.getResource().setex(
                //key 是通过用户电话和什么业务进行校验
                telephone + RedisMessageConstant.SENDTYPE_ORDER,
                //5分钟
                60 * 5,
                //value保存验证码
                code.toString()
        );
        //验证码发送成功
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result send4Login (String telephone){
        //生成随机6位数的验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6); //生成6位数字验证码
        try {
            //将验证码通过阿里云发送给用户
            //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());

            System.out.println("登录的验证码为【"+code+"】"); //由于未注册阿里云短信验证，在此模拟短信发送的验证码
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将数据存放到Redis中，并设置有效时间5分钟
        jedisPool.getResource().setex(
                //key 是通过用户电话和什么业务进行校验
                telephone + RedisMessageConstant.SENDTYPE_LOGIN,
                //5分钟
                60 * 5,
                //value保存验证码
                code.toString()
        );
        //验证码发送成功
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


}
