package com.swz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.swz.constant.MessageConstant;
import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.entity.Result;
import com.swz.pojo.CheckItem;
import com.swz.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author : 苏文致
 * @date Date : 2021年06月30日 15:17
 * @Description: TODO:
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    CheckItemService service;

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add (@RequestBody CheckItem checkItem){

        try {
            service.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

    }

    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public PageResult findPage (@RequestBody QueryPageBean queryPageBean){

        PageResult pageResult = service.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete (Integer id){

        try {
            service.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL_HAVEGROUP);
        } catch (Exception e2) {
            e2.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findById")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findById (Integer id){
        CheckItem checkItem = service.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result update (@RequestBody CheckItem checkItem){

        try {
            service.update(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }

    }
    @RequestMapping("/findAll")
    @PreAuthorize("hasAuthority('CHECHOTEM_QUERY')")
    public Result findAll (){
        try {
            List<CheckItem> checkItems = service.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
