package com.swz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.swz.constant.MessageConstant;
import com.swz.constant.RedisConstant;
import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.entity.Result;
import com.swz.pojo.CheckGroup;
import com.swz.pojo.CheckItem;
import com.swz.pojo.Setmeal;
import com.swz.service.SetmealService;
import com.swz.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @author : 苏文致
 * @date Date : 2021年07月07日 10:00
 * @Description: TODO:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService ;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        try{
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;

            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
            result.setData(fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
    @RequestMapping("/add")
    public Result add (@RequestBody Setmeal setmeal, @RequestParam("checkgroupIds") Integer[] ids){

        try {
            setmealService.add(setmeal,ids);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }

    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult add (@RequestBody QueryPageBean queryPageBean){
        PageResult result = setmealService.findPage(queryPageBean);
        return result;
    }

    //删除检查组
    @RequestMapping("/delete")
    public Result delete (Integer id){
        try {
            setmealService.delete(id);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

    }


