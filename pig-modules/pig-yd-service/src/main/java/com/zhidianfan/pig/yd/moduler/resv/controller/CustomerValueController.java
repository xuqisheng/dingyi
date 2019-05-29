package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.resv.service.BusinessCustomerAnalysisInfoService;
import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sjl
 * 2019-05-27 13:18
 */
@Slf4j
@RestController
@RequestMapping("/customervalue")
public class CustomerValueController {

    @Autowired
    private CustomerValueService customerValueService;

    @Autowired
    private BusinessCustomerAnalysisInfoService businessCustomerAnalysisInfoService;

    /**
     * 执行任务的入口-客户价值
     */
    @PostMapping("/task")
    public void gcustomerValue() {
        log.info("开始执行任务:{}", LocalDateTime.now());
        customerValueService.getCustomerValueBaseInfo();
        log.info("任务执行结束:{}", LocalDateTime.now());
    }

    /**
     * 客户价值分析
     * @param resvDate
     */
    @PostMapping("/analysis")
    public void customerAnalysis(String resvDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        log.info("开始执行任务:{}", formatter.format(LocalDateTime.now()));
        businessCustomerAnalysisInfoService.saveAnalysisDetail(resvDate);
        log.info("任务执行结束:{}", formatter.format(LocalDateTime.now()));
    }

}
