package com.swz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swz.dao.CheckGroupDao;
import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.pojo.CheckGroup;
import com.swz.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月02日 15:37
 * @Description: TODO:
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao groupDao;

    @Override
    public void add (CheckGroup checkGroup, Integer[] checkItemIds) throws RuntimeException{
        //添加组 ,主键返回
        groupDao.add(checkGroup);
        setCheckGroupAndCheckItem(checkGroup.getId(), checkItemIds);
    }

    @Override
    public PageResult findPage (QueryPageBean queryPageBean){
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = groupDao.findPage(queryPageBean.getQueryString());
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public void delete (Integer id){
        //先检查检查套餐中有没有这个项目，如果没有才能删除
        Integer num = groupDao.findSetMealGroup(id);
        if (num > 0) {
            throw new RuntimeException("套餐中存在这个检查组！");
        }else {
            //删除检测组和检测项中间表之间的该检测组项（存在外键就不可直接删除）
            groupDao.deleteCheckGroupAndCheckItem(id);
            groupDao.delete(id);

        }
    }

    /**
     * 根据id查询检测组信息
     */
    @Override
    public CheckGroup findCheckgroup (Integer id){
        CheckGroup checkGroup = groupDao.findCheckgroup(id);
        return checkGroup;
    }

    /**
     * 查询检测组对应检测项id
     */
    @Override
    public Integer[] findCheckGroupAssociationCheckItems (Integer id){
        Integer[] ids = groupDao.findCheckGroupAssociationCheckItems(id);
        return ids;
    }

    /**
     * 更新检查组
     */
    @Override
    public void update (Integer[] checkitemIds, CheckGroup checkGroup){
        //跟新检查组
        groupDao.update(checkGroup);
        //删除中间表检查组对应的的检查项、
        groupDao.deleteCheckGroupAndCheckItem(checkGroup.getId());
        //再将新的检查信息插入到中间表
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    @Override
    public List<CheckGroup> findAll (){
        return groupDao.findAll();
    }


    public void setCheckGroupAndCheckItem (Integer groupId, Integer[] checkItems){
        for (Integer checkItem : checkItems) {
            Map<String, Integer> map = new HashMap<>();
            if (checkItems != null && checkItems.length > 0) {
                map.put("checkgroupid", groupId);
                map.put("checkitemid", checkItem);
                groupDao.setCheckGroupAndCheckItem(map);
            } else {
                throw new RuntimeException("请选择检查项！");
            }
        }
    }


}
