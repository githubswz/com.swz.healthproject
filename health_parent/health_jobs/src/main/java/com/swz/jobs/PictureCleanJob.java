package com.swz.jobs;

import com.swz.constant.RedisConstant;
import com.swz.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author : 苏文致
 * @date Date : 2021年07月07日 12:18
 * @Description: TODO:
 */
@Component
public class PictureCleanJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        System.out.println(jedisPool);
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set!=null){
            for (String picName : set) {
                //删除七牛云服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                jedisPool.getResource().
                        srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
            }
        }
    }
}
