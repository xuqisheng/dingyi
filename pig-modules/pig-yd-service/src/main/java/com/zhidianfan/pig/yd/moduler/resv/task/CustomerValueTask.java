package com.zhidianfan.pig.yd.moduler.resv.task;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigTaskExec;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.LossValueConfig;
import com.zhidianfan.pig.yd.moduler.common.service.IConfigTaskExecService;
import com.zhidianfan.pig.yd.moduler.resv.constants.CustomerValueConstants;
import com.zhidianfan.pig.yd.moduler.resv.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private IConfigTaskExecService iConfigTaskExecService;

    @Autowired
    private LossValueConfigService lossValueConfigService;

    @Autowired
    private CustomerValueInitService customerValueInitService;


    @Scheduled(cron = "0 30 20 * * ?")
    public void task() {
        log.info("生成任务,时间:[{}]", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        customerValueTaskService.addCustomerList();
//        flag = Boolean.TRUE;
        log.info("任务结束，时间[{}]", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }


    @Scheduled(fixedDelay = 10_000)
    public void customerValue2() {
        ConfigTaskExec execTime = getExecTime();
        if (execTime == null) {
            log.error("未添加数据库执行时间，请检查 config_task_exec 配置表");
            return;
        }
        LocalTime startTime1 = execTime.getStartTime();
        if (startTime1 == null) {
            log.error("未添加数据库开始执行时间，请检查 config_task_exec 配置表");
            return;
        }
        LocalTime startTime2 = execTime.getEndTime();
        if (startTime2 == null) {
            log.error("未添加数据库结束执行时间，请检查 config_task_exec 配置表");
            return;
        }

        if (LocalTime.now().isAfter(startTime1) || LocalTime.now().isBefore(startTime2)) {
            LocalDateTime startTime = LocalDateTime.now();
            LocalDate localDate = LocalDate.now();
            if (LocalTime.now().isBefore(startTime2)) {
                //批次减一天
                localDate = localDate.minusDays(1);
            }
            log.info("准备计算:{} 的客户价值数据", localDate);
            // 指明当前要计算的批次
            List<com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask> customerValuesValueTask = customerValueTaskService.getCustomerValuesValueTask(localDate);
            Map<Integer, List<LossValueConfig>> lossValueConfigMap = lossValueConfigService.getLossValueConfigList();
            Optional.ofNullable(customerValuesValueTask)
                    .ifPresent(customerValueTasks -> {
                        int nThreads = 16;
                        AtomicInteger count = new AtomicInteger(customerValuesValueTask.size());
                        log.info("本次有:{}家酒店需要计算客户价值", count);
                        ExecutorService executorService = new ThreadPoolExecutor(nThreads, nThreads,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>());

                        List<CompletableFuture<Long>> completableFutures = new ArrayList<>(customerValuesValueTask.size());
                        for (int i = 0; i < customerValuesValueTask.size(); i++) {
                            com.zhidianfan.pig.yd.moduler.common.dao.entity.CustomerValueTask tmp = customerValuesValueTask.get(i);
                            Long hotelId = tmp.getHotelId();
                            int businessId = 0;
                            if (hotelId != null) {
                                try {
                                    businessId = Integer.parseInt(String.valueOf(hotelId));
                                } catch (NumberFormatException ignored) {
                                }
                            }
                            final List<LossValueConfig> lossValueConfigList;
                            List<LossValueConfig> lossValueConfigListTmp = lossValueConfigMap.get(businessId);
                            if (CollectionUtils.isEmpty(lossValueConfigListTmp)) {
                                customerValueInitService.initSubValue(String.valueOf(hotelId));
                                lossValueConfigList = lossValueConfigService.getLossValueConfig(businessId);
                            } else {
                                lossValueConfigList = lossValueConfigListTmp;
                            }
                            CompletableFuture<Long> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
                                customerValueService.getCustomerValueBaseInfo2(tmp, 2000, execTime, lossValueConfigList);
                                return hotelId;
                            }, executorService);
                            completableFutures.add(integerCompletableFuture);
                        }

                        CompletableFuture[] completableFutures1 = new CompletableFuture[completableFutures.size()];
                        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutures.toArray(completableFutures1));
                        try {
                            voidCompletableFuture.get();
                            completableFutures.stream()
                                    .forEach(tmp -> {
                                        try {
                                            log.info("酒店id：{}执行结束", tmp.get());
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

                        executorService.shutdown();

                    });
            LocalDateTime endTime = LocalDateTime.now();
            Duration duration = Duration.between(startTime, endTime);
            log.info("--------------------------------任务执行消耗总时间：[{}] 秒--------------------------------", duration.getSeconds());
        } else {
            log.info("未到执行客户价值时间......");
        }

    }



    private ConfigTaskExec getExecTime() {
        return iConfigTaskExecService.selectById(1);
    }


    /**
     * 客户分析详情数据
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void customerAnalysis() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        log.info("客户分析详情数据开始执行任务:[{}]", formatter.format(LocalDateTime.now()));
        businessCustomerAnalysisInfoService.execute();
        log.info("客户分析详情数据任务执行结束:[{}]", formatter.format(LocalDateTime.now()));
    }

}
