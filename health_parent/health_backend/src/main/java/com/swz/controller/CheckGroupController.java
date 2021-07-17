package com.swz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.swz.constant.MessageConstant;
import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.entity.Result;
import com.swz.pojo.CheckGroup;
import com.swz.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月02日 15:30
 * @Description: TODO:
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    CheckGroupService groupService;

    //添加检测组
    @RequestMapping("/add")
    public Result add (@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){

        try {
            groupService.add(checkGroup, checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult add (@RequestBody QueryPageBean queryPageBean){
        PageResult result = groupService.findPage(queryPageBean);
        return result;
    }

    //删除检查组
    @RequestMapping("/delete")
    public Result delete (Integer id){
        try {
            groupService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    //查询检查组
    @RequestMapping("/findCheckgroup")
    public Result findCheckgroup (Integer id){
        try {
            CheckGroup checkGroup = groupService.findCheckgroup(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //从检测组—检测项中查询检测组对应的检测项信息
    @RequestMapping("/findCheckGroupAssociationCheckItems")
    public Result findCheckGroupAssociationCheckItems (Integer id){
        try {
            Integer[] ids = groupService.findCheckGroupAssociationCheckItems(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //更新
    @RequestMapping("/update")
    public Result update (@RequestParam("checkitemIds") Integer[] checkitemIds, @RequestBody CheckGroup checkGroup){
        try {
            groupService.update(checkitemIds, checkGroup);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll (){
        List<CheckGroup> checkGroupList = groupService.findAll();
        if (checkGroupList != null && checkGroupList.size() > 0) {
            Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(checkGroupList);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
}