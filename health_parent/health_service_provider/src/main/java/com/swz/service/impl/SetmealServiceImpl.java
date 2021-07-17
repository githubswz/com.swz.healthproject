package com.swz.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.swz.constant.RedisConstant;
import com.swz.dao.SetmealDao;
import com.swz.entity.PageResult;
import com.swz.entity.QueryPageBean;
import com.swz.pojo.CheckGroup;
import com.swz.pojo.Setmeal;
import com.swz.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月07日 10:02
 * @Description: TODO:
 */
@Service(interfaceClass= SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService{
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    //freemark配置文件
    @Autowired
    private FreeMarkerConfig freemarkerConfig;

    @Value("${out_put_path}")//从属性文件读取输出目录的路径
    private String outputpath ;

    @Override
    public void add (Setmeal setmeal, Integer[] ids){
        //添加组 ,主键返回
        setmealDao.add(setmeal);
        setSetmealAndCheckGroup(setmeal.getId(), ids);
        //保存图片到redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

        //生成freemark静态页面
        //新增套餐后需要重新生成静态页面
        generateMobileStaticHtml();
    }

    //生成静态页面
    public void generateMobileStaticHtml() {
        //准备模板文件中所需的数据
        List<Setmeal> setmealList = this.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("setmealList", setmealList);
        this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",dataMap);
    }

    //生成套餐详情静态页面（多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("setmeal", this.findById(setmeal.getId()));
            this.generateHtml("mobile_setmeal_detail.ftl",
                    "setmeal_detail_"+setmeal.getId()+".html",
                    dataMap);
        }
    }

    /**
     * @param templateName 模版的名称 （因为在配置freemarkerConfig的Bean时配置了目录所以只需要配置模版名称）
     * @param htmlPageName 生成的Html静态页面的名称
     * @param dataMap 需要准备的数据，这些数据用对freemark进行页面数据的动态填充
     */
    public void generateHtml(String templateName,String htmlPageName,Map<String, Object> dataMap){
        Configuration configuration = freemarkerConfig.getConfiguration();
        Writer out = null;
        try {
            // 加载模版文件
            Template template = configuration.getTemplate(templateName);
            // 生成数据
            File docFile = new File(outputpath + "\\" + htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // 输出文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override
    public PageResult findPage (QueryPageBean queryPageBean){
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById (Integer id){
        return setmealDao.findById(id);
    }

    @Override
    public void delete (Integer id){
        //删除套餐-检查组
        setmealDao.deleteSetMealAndCheckGroup(id);
        //删除套餐
        setmealDao.delete(id);
    }

    private void setSetmealAndCheckGroup (Integer mealId, Integer[] ids){
        if (ids != null && ids.length > 0) {
        for (Integer checkGroupId : ids) {
            Map<String, Integer> map = new HashMap<>();

                map.put("setmeal_id", mealId);
                map.put("checkgroup_id", checkGroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }else {
            throw new RuntimeException("请选择检查组！");
        }
    }
}
