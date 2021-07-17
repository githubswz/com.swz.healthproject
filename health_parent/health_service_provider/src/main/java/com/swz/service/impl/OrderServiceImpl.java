package com.swz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.swz.dao.MemberDao;
import com.swz.dao.OrderDao;
import com.swz.dao.OrderSettingDao;
import com.swz.dao.SetmealDao;
import com.swz.exception.OrderException;
import com.swz.pojo.Member;
import com.swz.pojo.Order;
import com.swz.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月11日 10:56
 * @Description: TODO:
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public Integer add (Map map) throws Exception{
        String orderDate = (String) map.get("orderDate");
        String setmealID = (String) map.get("setmealId");
        String idCard = (String) map.get("idCard");
        String telphone = (String) map.get("telephone");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(orderDate);

        // 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        if (orderSettingDao.find(date) < 0) {
            throw new OrderException("您选择的预约日期未开放");
        }
        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        Integer reservation = orderSettingDao.findReservationByDate(date);
        if (reservation == null) {
            reservation = 0;
        }
        Integer number = orderSettingDao.findNumberByDate(date);
        if (number == null) {
            number = 0;
        }
        if (number <= reservation) {
            throw new OrderException("预约人数已满");
        }

        //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        Member member = memberDao.findMenberByTel(telphone);
        if (member == null) {
            //创建会员
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard(idCard);
            member.setPhoneNumber((String) map.get("telephone"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        int n = orderDao.findStemealByDate(date, setmealID, member.getId());
        if (n > 0) {
            throw new OrderException("不可重复预约");
        }
        //5、预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);
        orderSettingDao.updateReservationById(date, reservation + 1);
        return order.getId();
    }

    @Override
    public Map<String, String> findById (Integer id){
        Map map = orderDao.findById(id);
        if (map != null) {
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String format1 = format.format(orderDate);
            map.put("orderDate", format1);
        }
        return map;

    }


}
