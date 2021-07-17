package com.swz.service;

import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.pojo.CheckGroup;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月02日 15:33
 * @Description: TODO:
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup , Integer[] checkItemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete (Integer id);

    CheckGroup findCheckgroup (Integer id);

    Integer[] findCheckGroupAssociationCheckItems (Integer id);

    void update (Integer[] checkitemIds, CheckGroup checkGroup);

    List<CheckGroup> findAll ();
}
