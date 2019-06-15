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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-06-14 19:21
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "yd.task", havingValue = "true")
public class CustomerValueTask {

    @Autowired
    private CustomerValueService customerValueService;

    @Autowired
    private BusinessCustomerAnalysisInfoService businessCustomerAnalysisInfoService;

    @Autowired
    private CustomerValueTaskService customerValueTaskService;


    @Scheduled(cron = "0 30 20 * * ?")
    public void task() {
        log.info("生成任务,时间:[{}]", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        customerValueTaskService.addCustomerList();
//        flag = Boolean.TRUE;
        log.info("任务结束，时间[{}]", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    //    @Scheduled(fixedDelay = 1000)
    public void customerValue() {
        LocalTime startTime1 = LocalTime.of(23, 0, 0);
        LocalTime startTime2 = LocalTime.of(8, 0, 0);
        LocalTime nowTime = LocalTime.now();
        if (nowTime.isAfter(startTime1) || nowTime.isBefore(startTime2)) {
            log.info("开始执行客户价值定时任务，跑客户价值相关数据");
//            customerValueService.getCustomerValueBaseInfo();
        }
    }

    @Scheduled(fixedDelay = 10_000)
    public void customerValue2() {
        LocalTime startTime1 = LocalTime.of(23, 0, 0);
        LocalTime startTime2 = LocalTime.of(8, 0, 0);
        LocalTime nowTime = LocalTime.now();
        if (nowTime.isAfter(startTime1) || nowTime.isBefore(startTime2)) {
            LocalDate localDate = LocalDate.now();
            if (nowTime.isBefore(startTime2)) {
                //批次减一天
                localDate = localDate.minusDays(1);
            }
            log.info("准备计算:{} 的客户价值数据", localDate);
//            指明当前要计算的批次
            List<com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask> customerValuesValueTask = customerValueTaskService.getCustomerValuesValueTask(localDate);

            Optional.ofNullable(customerValuesValueTask)
                    .ifPresent(customerValueTasks -> {

                        int nThreads = 64;
                        AtomicInteger count = new AtomicInteger(customerValuesValueTask.size());
                        log.info("本次有:{}家酒店需要计算客户价值", count);
                        ExecutorService executorService = new ThreadPoolExecutor(nThreads, nThreads,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>());

                        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "16");

                        List<Long> collect = customerValueTasks.parallelStream()
                                .map(customerValueTask -> CompletableFuture.supplyAsync(() -> {
                                    log.info("{}开始被处理，剩余{}家待处理", customerValueTask.getHotelId(), count);
                                    if (nowTime.isAfter(startTime1) || nowTime.isBefore(startTime2)) {
                                        customerValueService.getCustomerValueBaseInfo2(customerValueTask);
                                        count.addAndGet(-1);
                                        log.info("{}处理结束，剩余{}家待处理", customerValueTask.getHotelId(), count);
                                    } else {
                                        while (!(nowTime.isAfter(startTime1) || nowTime.isBefore(startTime2))) {
                                            log.info("非客户价值计算时间暂停执行======");
                                            try {
                                                TimeUnit.MINUTES.sleep(10);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    return customerValueTask.getHotelId();
                                }, executorService))
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList());

                        log.info("处理结束，本次计算:{}这些酒店", collect);

                        executorService.shutdown();

                    });
        } else {
            log.info("未到执行客户价值时间......");
        }

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "32");
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
