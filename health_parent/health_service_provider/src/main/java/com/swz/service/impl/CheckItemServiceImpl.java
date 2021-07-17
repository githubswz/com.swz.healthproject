package com.swz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swz.dao.CheckItemDao;
import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.pojo.CheckItem;
import com.swz.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author : 苏文致
 * @date Date : 2021年06月30日 15:25
 * @Description: TODO:
 */

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckItemDao dao;

    @Override
    public void add (CheckItem checkItem){
        dao.add(checkItem);
    }

    @Override
    public PageResult findPage (QueryPageBean queryPageBean){
        //设置分页的起始页码和查询的条数，实际上是通过线程注入将这里两个值植入到查询的线程中
        //System.out.println(queryPageBean.getCurrentPage()+"   "+queryPageBean.getPageSize());
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = dao.findPage(queryPageBean.getQueryString());
        long total = page.getTotal();
        List<CheckItem> result = page.getResult();
        return new PageResult(total, result);
    }

    @Override
    public void delete (Integer id) throws RuntimeException{
        //先检测检测组是否有该项目，有就不能删
        int count = dao.findCountByCheckItemId(id);
        if (count > 0) {
            throw new RuntimeException("检测组有值");
        } else {
            dao.deleteById(id);
        }
    }

    @Override
    public CheckItem findById (Integer id){
        CheckItem checkItem = dao.findById(id);
        return checkItem;
    }

    @Override
    public void update (CheckItem item){
        dao.update(item);
    }

    @Override
    public List<CheckItem> findAll (){
        List<CheckItem> checkItems = dao.findAll();
        return checkItems;
    }
}
