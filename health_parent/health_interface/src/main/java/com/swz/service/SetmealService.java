package com.swz.service;

import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.pojo.Setmeal;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月07日 10:02
 * @Description: TODO:
 */
public interface SetmealService {
    void add (Setmeal setmeal, Integer[] ids);

    PageResult findPage (QueryPageBean queryPageBean);

    List<Setmeal> findAll ();

    Setmeal findById (Integer id);

    void delete (Integer id);
}
