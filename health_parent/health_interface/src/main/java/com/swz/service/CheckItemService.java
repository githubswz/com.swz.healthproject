package com.swz.service;

import com.github.pagehelper.Page;
import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.entity.Result;
import com.swz.pojo.CheckItem;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年06月30日 15:20
 * @Description: TODO:
 */
public interface CheckItemService {

    public void add(CheckItem checkItem);

    public PageResult findPage(QueryPageBean queryPageBean);

    public void delete(Integer id);

    public CheckItem findById(Integer id);

    public void update(CheckItem item);

    public List<CheckItem> findAll ();



}
