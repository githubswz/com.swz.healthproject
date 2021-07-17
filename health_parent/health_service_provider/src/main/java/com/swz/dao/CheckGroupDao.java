package com.swz.dao;

import com.github.pagehelper.Page;
import com.swz.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年06月30日 22:43
 * @Description: TODO:
 */
public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    /**
     * 关联检测项和检测组
     */
    void setCheckGroupAndCheckItem(Map<String,Integer> map);

    Page<CheckGroup> findPage (String queryString);

    /**
     * 查询检查套餐和检测组的中间表
     */
    Integer findSetMealGroup (Integer id);

    void delete(Integer id);

    /**
     * 删除中间表的检查组
     */
    void deleteCheckGroupAndCheckItem (Integer id);
    /**
     *查询检测组
     */
    CheckGroup findCheckgroup (Integer id);

    Integer[] findCheckGroupAssociationCheckItems (Integer id);

    /**
     * 更新
     */
    void update (CheckGroup checkGroup);

    List<CheckGroup> findAll ();

    /**
     * 根据套餐的id查询检查组信息
     * @param id
     * @return
     */
    List<CheckGroup> findBySetmealId(Integer id);
}
