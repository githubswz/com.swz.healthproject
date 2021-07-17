package com.swz.dao;

import com.github.pagehelper.Page;
import com.swz.pojo.CheckItem;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年06月30日 15:28
 * @Description: TODO:
 */
public interface CheckItemDao {
    /**
     * 添加检测项
     */
    void add (CheckItem checkItem);

    /**
     * 分页查询
     *
     * @param queryString 条件查询条件
     */
    Page<CheckItem> findPage (String queryString);

    /**
     * 根据id删除检查项
     */
    void deleteById (Integer id);

    /**
     * 通过检查项的数量
     *
     * @param id
     */
    int findCountByCheckItemId (Integer id);

    /**
     * 根据id查
     */
    CheckItem findById (Integer id);

    void update (CheckItem checkItem);

    List<CheckItem> findAll ();

    /**
     * 根据检查组的信息查询检查项
     */
    List<CheckItem> findByCheckGroupId (Integer id);

}
