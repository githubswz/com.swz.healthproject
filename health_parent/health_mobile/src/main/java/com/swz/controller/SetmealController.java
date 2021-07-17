package com.swz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.swz.constant.MessageConstant;
import com.swz.entity.Result;
import com.swz.pojo.Setmeal;
import com.swz.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author : 苏文致
 * @date Date : 2021年07月09日 9:56
 * @Description: TODO:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    //获取所有套餐信息
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try{
            List<Setmeal> list = setmealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }





}
