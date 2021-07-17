package com.swz.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.swz.constant.MessageConstant;
import com.swz.entity.Result;
import com.swz.service.ReporterService;
import com.swz.utils.DateUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 13:19
 * @Description: TODO:
 */
@RestController
@RequestMapping("/report")
public class ReporterController {

    @Reference
    ReporterService reporterService;

    /**
     * 查询近一个月的会员注册登录情况
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport (){
        try {
            Map<String, Object> map = reporterService.getMemberReport();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport (){

        try {
            Map<String, Object> map = reporterService.getSetmealReport();
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData (){

        try {
            Map<String, Object> map = reporterService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    //导出Excel表格
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport (HttpServletRequest request, HttpServletResponse response) throws Exception{
        //获取数据
        Map<String, Object> map = reporterService.getBusinessReportData();
        String reportDate = DateUtils.parseDate2String((Date) map.get("reportDate"));

        Integer todayNewMember = (Integer) map.get("todayNewMember");
        Integer totalMember = (Integer) map.get("totalMember");
        Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
        Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
        Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
        Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
        Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
        Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");

        //获得Excel模板文件绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(realPath);
            XSSFSheet sheet = workbook.getSheet("sheet1");
            XSSFRow row = sheet.getRow(2);
            XSSFCell cell1 = row.getCell(5);//获取第三行数据第六列格子
            cell1.setCellValue(reportDate); //设置数据

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map map1 : hotSetmeal) {//热门套餐
                String name = (String) map1.get("name");
                Long setmeal_count = (Long) map1.get("setmeal_count");
                double proportion = (double) map1.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion);//占比
            }

            //通过response拿到输出流
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, "生成报表失败");
    }

    //导出PDF报表
    @RequestMapping("/exportBusinessReportPDF")
    public Result exportBusinessReportPDF (HttpServletRequest request, HttpServletResponse response) throws Exception{

        try {
            //获取数据
            Map<String, Object> map = reporterService.getBusinessReportData();
            String reportDate = DateUtils.parseDate2String((Date) map.get("reportDate"));
            map.put("reportDate",reportDate); //文档中为String类型。需要将date类型转为String类型

            List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");

            //获得jrxml文件
            String jrxmlPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jrxml";
            //编译生成jasper文件的路径
            String jasperPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jasper";
            //编译模板
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
            //填充数据---使用JavaBean数据源方式填充
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperPath, map, new JRBeanCollectionDataSource(hotSetmeal));

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("content-Disposition", "attachment;filename=report.pdf");
            //输出文件
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "生成报表失败");
        }

    }


}
