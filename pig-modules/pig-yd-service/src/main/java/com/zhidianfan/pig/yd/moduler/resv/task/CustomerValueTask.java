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
import java.util.ArrayList;
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
        if (LocalTime.now().isAfter(startTime1) || LocalTime.now().isBefore(startTime2)) {
            LocalDate localDate = LocalDate.now();
            if (LocalTime.now().isBefore(startTime2)) {
                //批次减一天
                localDate = localDate.minusDays(1);
            }
            log.info("准备计算:{} 的客户价值数据", localDate);
//            指明当前要计算的批次
            List<com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask> customerValuesValueTask = customerValueTaskService.getCustomerValuesValueTask(localDate);

            Optional.ofNullable(customerValuesValueTask)
                    .ifPresent(customerValueTasks -> {

                        int nThreads = 16;
                        AtomicInteger count = new AtomicInteger(customerValuesValueTask.size());
                        log.info("本次有:{}家酒店需要计算客户价值", count);
                        ExecutorService executorService = new ThreadPoolExecutor(nThreads, nThreads,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>());

                        for (int i = 0; i < customerValuesValueTask.size(); ) {

                            List<CompletableFuture<Long>> completableFutures = new ArrayList<>(nThreads);
                            for (int j = 0; j < nThreads && i < customerValuesValueTask.size(); j++, i++) {
                                com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask tmp = customerValuesValueTask.get(i);
                                CompletableFuture<Long> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
                                    customerValueService.getCustomerValueBaseInfo2(tmp, 500);
                                    return tmp.getHotelId();
                                }, executorService);
                                completableFutures.add(integerCompletableFuture);
                            }

                            CompletableFuture[] completableFutures1 = new CompletableFuture[completableFutures.size()];
                            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutures.toArray(completableFutures1));
                            try {
//                堵塞，等待这批任务全部完成，再进行下一批
                                voidCompletableFuture.get();
                                int finalI = i;
                                completableFutures.stream()
                                        .forEach(tmp -> {
                                            try {
                                                log.info("酒店id：{}执行结束，剩余酒店数：{}", tmp.get(), (customerValuesValueTask.size() - finalI));
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            } catch (ExecutionException e) {
                                                e.printStackTrace();
                                            }
                                        });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }


                        }

                        executorService.shutdown();

                        executorService.shutdown();

                    });
        } else {
            log.info("未到执行客户价值时间......");
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
