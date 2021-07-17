package com.swz.service;

import com.swz.exception.OrderException;
import com.swz.pojo.Member;
import com.swz.pojo.Order;

import java.text.ParseException;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月11日 10:53
 * @Description: TODO:
 */
public interface OrderService {

    Integer add (Map map) throws Exception;

    Map findById (Integer id);
}
