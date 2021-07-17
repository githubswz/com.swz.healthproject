package com.swz.dao;

import com.github.pagehelper.Page;
import com.swz.pojo.CheckGroup;
import com.swz.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月07日 10:04
 * @Description: TODO:
 */
public interface SetmealDao {
    void add (Setmeal setmeal);

    void setSetmealAndCheckGroup (Map<String, Integer> map);

    Page<Setmeal> findPage (String queryString);

    List<Setmeal> findAll ();

    Setmeal findById (Integer id);

    /**
     * 根据id删除中间表
     */
    void deleteSetMealAndCheckGroup (Integer id);

    /**
     * 根据id删除套餐
     * @param id
     */
    void delete (Integer id);
}
