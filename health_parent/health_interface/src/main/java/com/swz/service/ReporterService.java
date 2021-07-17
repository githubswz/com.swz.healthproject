package com.swz.service;

import java.util.Map;

/**
 * @author : 苏文致
 * @date Date : 2021年07月13日 13:36
 * @Description: TODO:
 */
public interface ReporterService {
    Map<String, Object> getMemberReport ();

    Map<String, Object> getSetmealReport ();

    Map<String, Object> getBusinessReportData ();
}
