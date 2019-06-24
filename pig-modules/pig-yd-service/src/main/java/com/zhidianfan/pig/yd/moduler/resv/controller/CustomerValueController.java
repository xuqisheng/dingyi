package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask;
import com.zhidianfan.pig.yd.moduler.resv.dto.CommonRes;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessCustomerAnalysisInfoService;
import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueInitService;
import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueService;
import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
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

    @Autowired
    private CustomerValueInitService customerValueInitService;

    /**
     * 生成待执行的任务
     * @return
     */
    @PostMapping("/task")
    public ResponseEntity task() {
        log.info("生成任务,时间:[{}]", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        customerValueTaskService.addCustomerList();
        log.info("任务结束，时间[{}]", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        return ResponseEntity.ok(CommonRes.SUCCESS);
    }

    /**
     * 执行任务的入口-客户价值
     */
//    @PostMapping("/customervalue")
//    public ResponseEntity gcustomerValue() {
//        log.info("开始执行任务:[{}]", LocalDateTime.now());
//        customerValueService.getCustomerValueBaseInfo2();
//        log.info("任务执行结束:[{}]", LocalDateTime.now());
//        return ResponseEntity.ok(CommonRes.SUCCESS);
//    }

    /**
     * 客户价值分析
     */
    @PostMapping("/analysis")
    public ResponseEntity customerAnalysis() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        log.info("开始执行任务:[{}]", formatter.format(LocalDateTime.now()));
        businessCustomerAnalysisInfoService.execute();
        log.info("任务执行结束:[{}]", formatter.format(LocalDateTime.now()));
        return ResponseEntity.ok(CommonRes.SUCCESS);
    }

    /**
     * 客户价值-一级价值初始化
     * @param businessId 酒店 id
     */
    @GetMapping("/init/config/firstvalue")
    public ResponseEntity initFirstvalue(@RequestParam(required = false) String businessId) {
        LocalDateTime start = LocalDateTime.now();
        log.error("开始执行一级价值的初始化配置，参数：[{}]，开始执行时间：[{}]", businessId, start.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        customerValueInitService.initFirstValue(businessId);
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(start, end);
        log.info("一级分类初始化配置完毕，任务结束时间：[{}], 任务执行时间：[{}] 秒", end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), between.toMinutes());
        return ResponseEntity.ok(CommonRes.SUCCESS);
    }

    /**
     * 细分价值初始初始化
     * @param businessId 酒店id
     */
    @GetMapping("/init/config/subvalue")
    public ResponseEntity initSubValue(@RequestParam(required = false) String businessId) {
        LocalDateTime start = LocalDateTime.now();
        log.error("开始执行细分价值的初始化配置，参数：[{}]，开始执行时间：[{}]", businessId, start.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        customerValueInitService.initSubValue(businessId);
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(start, end);
        log.info("细分价值初始化配置完毕，任务结束时间：[{}], 任务执行时间：[{}] 秒", end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), between.toMinutes());
        return ResponseEntity.ok(CommonRes.SUCCESS);
    }
}
