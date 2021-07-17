package com.swz.dao;

import com.swz.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月11日 10:58
 * @Description: TODO:
 */
public interface OrderDao {

    Integer findNumberByDate (Date date);

    Integer findStemealByDate (@Param("date") Date date,@Param("setmealId") String setmealID,@Param("memberId") Integer memberId);

    void add (Order order);

    Map findById (Integer id);
}
