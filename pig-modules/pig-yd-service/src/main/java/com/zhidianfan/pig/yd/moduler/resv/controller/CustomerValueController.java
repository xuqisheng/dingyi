package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessCustomerAnalysisInfoService;
import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueService;
import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sjl
 * 2019-05-27 13:18
 */
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerValueController {

    @Autowired
    private CustomerValueService customerValueService;

    @Autowired
    private BusinessCustomerAnalysisInfoService businessCustomerAnalysisInfoService;

    @Autowired
    private CustomerValueTaskService customerValueTaskService;

    /**
     * 生成待执行的任务
     * @return
     */
    @PostMapping("/task")
    public ResponseEntity task(@RequestParam(required = false) String date) {
        log.info("生成任务");
        customerValueTaskService.addCustomerList(date);
        return ResponseEntity.ok().build();
    }

    /**
     * 执行任务的入口-客户价值
     */
    @PostMapping("/customervalue")
    public ResponseEntity gcustomerValue() {
        log.info("开始执行任务:{}", LocalDateTime.now());
        customerValueService.getCustomerValueBaseInfo();
        log.info("任务执行结束:{}", LocalDateTime.now());
        return ResponseEntity.ok().build();
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
