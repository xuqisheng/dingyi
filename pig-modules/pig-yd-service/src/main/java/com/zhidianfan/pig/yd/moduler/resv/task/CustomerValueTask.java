package com.zhidianfan.pig.yd.moduler.resv.task;

import com.zhidianfan.pig.yd.moduler.resv.service.BusinessCustomerAnalysisInfoService;
import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueService;
import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author sjl
 * 2019-06-14 19:21
 */@Slf4j
@Component
@ConditionalOnProperty(name = "yd.task", havingValue = "true")
public class CustomerValueTask {

    @Autowired
    private CustomerValueService customerValueService;

    @Autowired
    private BusinessCustomerAnalysisInfoService businessCustomerAnalysisInfoService;

    @Autowired
    private CustomerValueTaskService customerValueTaskService;

//    private Boolean flag = Boolean.FALSE;

    @Scheduled(cron = "0 30 22 * * ?")
    public void task() {
        log.info("生成任务,时间:[{}]", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        customerValueTaskService.addCustomerList();
//        flag = Boolean.TRUE;
        log.info("任务结束，时间[{}]", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    @Scheduled(fixedDelay = 1000)
    public void customerValue() {
        LocalTime startTime1 = LocalTime.of(23, 0, 0);
        LocalTime startTime2 = LocalTime.of(8, 0, 0);
        LocalTime nowTime = LocalTime.now();
        if (nowTime.isAfter(startTime1) || nowTime.isBefore(startTime2)) {
            log.info("开始执行客户价值定时任务，跑客户价值相关数据");
            customerValueService.getCustomerValueBaseInfo();
//            flag = Boolean.FALSE;
        }
    }


    /**
     * 客户分析详情数据
     */
    @Scheduled(cron = "2 0 0 * * ?")
    public void customerAnalysis() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        log.info("开始执行任务:[{}]", formatter.format(LocalDateTime.now()));
        LocalDate initDate = LocalDate.now();
        boolean after = initDate.isAfter(LocalDate.of(2019, 6, 1));
        if (after) {
            log.info("开始执行初始化数据");
            LocalDate now = LocalDate.now();
            String resvDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            businessCustomerAnalysisInfoService.saveAnalysisDetail(resvDate);
        } else {
            LocalDate starDate = LocalDate.of(2018, 10, 1);
            LocalDate endDate = LocalDate.now();
            long month = starDate.until(endDate, ChronoUnit.MONTHS);
            for (int i = 1; i < month; i++) {
                LocalDate date = starDate.plusMonths(i);
                String resvDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                businessCustomerAnalysisInfoService.saveAnalysisDetail(resvDate);
            }
        }
        log.info("任务执行结束:[{}]", formatter.format(LocalDateTime.now()));
    }
}
