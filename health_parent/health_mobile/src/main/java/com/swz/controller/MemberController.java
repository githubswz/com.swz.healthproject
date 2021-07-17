package com.swz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.swz.constant.MessageConstant;
import com.swz.constant.RedisMessageConstant;
import com.swz.entity.Result;
import com.swz.pojo.Member;
import com.swz.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月12日 10:05
 * @Description: TODO:
 */
@RestController
@RequestMapping("/login")
public class MemberController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    //使用手机号和验证码登录
    @RequestMapping("/check")
    public Result login (HttpServletResponse response, @RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        if (validateCode != null && !validateCode.equals("")) {
            //查询redis
            String redisValidateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
            if (validateCode.equals(redisValidateCode)) {
                //登录成功....
                //判断是否为会员，否则创建用户
                Member member = memberService.findByTelephone(telephone);
                if (member == null) {
                    //创建
                    //当前用户不是会员，自动完成注册
                    member = new Member();
                    member.setPhoneNumber(telephone);
                    member.setRegTime(new Date());
                    memberService.add(member);
                }

                //添加cookie用于客户跟踪
                Cookie cookie = new Cookie("login_member_telephone",telephone);
                cookie.setMaxAge(60*60*24*30); //一个月的有效期
                cookie.setPath("/"); //路径
                response.addCookie(cookie);//将cookie伴随响应传递到浏览器
                //将数据保存到redis,后续可以根据cookie拿到客户身份，再将客户的身份在redis中查询出来
                String memmerJson = JSON.toJSON(member).toString();
                jedisPool.getResource().setex(telephone,60*60*24*30,memmerJson);
                return new Result(true, MessageConstant.LOGIN_SUCCESS);
            }
        }
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
    }




}
