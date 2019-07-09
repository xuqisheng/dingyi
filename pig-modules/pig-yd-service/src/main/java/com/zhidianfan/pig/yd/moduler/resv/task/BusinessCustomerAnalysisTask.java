package com.zhidianfan.pig.yd.moduler.resv.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysisDetail;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysisInfoTask;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessCustomerAnalysisDetailService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessCustomerAnalysisInfoTaskService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-06-23 12:01
 */
@Component
public class BusinessCustomerAnalysisTask {

    @Autowired
    private IBusinessCustomerAnalysisInfoTaskService iTaskService;

    @Autowired
    private IBusinessService iBusinessService;

    @Autowired
    private IBusinessCustomerAnalysisDetailService iBusinessCustomerAnalysisDetailService;

    /**
     * 初始化时，生成以前所有的酒店的计算月份
     */
    public void generatorTaskList() {

        List<Business> businessList = getBusinessList();
        List<BusinessCustomerAnalysisInfoTask> list = new ArrayList<>(businessList.size());

        // 1000 * 8
        for (Business business : businessList) {
            List<String> detailDate = getDetailDate(business.getId());
            List<BusinessCustomerAnalysisInfoTask> tasks = detailDate.parallelStream()
                    .distinct()
                    .map(date -> {
                        BusinessCustomerAnalysisInfoTask task = new BusinessCustomerAnalysisInfoTask();
                        task.setBusinessId(business.getId());
                        // LocalDate now = LocalDate.now();
                        // String date = DateTimeFormatter.ofPattern("yyyy-MM").format(resvDate);
                        task.setDate(date);
                        task.setUseTag(0);
                        task.setUpdateTime(LocalDateTime.now());
                        return task;
                    })
                    .collect(Collectors.toList());
             // list.addAll(tasks);
            if (CollectionUtils.isNotEmpty(tasks)) {
                iTaskService.insertBatch(tasks);
            }
        }
    }

    /**
     * 每个月 1 号，生成所有酒店的任务
     */
    @Scheduled(cron = "0 0 1 1 1/1 ?")
    public void generatorTask() {
        List<Business> businessList = getBusinessList();
        List<BusinessCustomerAnalysisInfoTask> list = new ArrayList<>(businessList.size());
        for (Business business : businessList) {
            BusinessCustomerAnalysisInfoTask task = new BusinessCustomerAnalysisInfoTask();
            task.setBusinessId(business.getId());
            LocalDate now = LocalDate.now().minusMonths(1);
            String date = DateTimeFormatter.ofPattern("yyyy-MM").format(now);
            task.setDate(date);
            task.setUseTag(0);
            task.setUpdateTime(LocalDateTime.now());
            list.add(task);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            iTaskService.insertBatch(list, 500);
        }
    }

    private List<String> getDetailDate(Integer id) {
        Wrapper<BusinessCustomerAnalysisDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("business_id", id);
        List<BusinessCustomerAnalysisDetail> list = iBusinessCustomerAnalysisDetailService.selectList(wrapper);

        List<String> dateList = list.stream()
                .map(BusinessCustomerAnalysisDetail::getDate)
                .collect(Collectors.toList());

        return dateList;
    }

    public List<Business> getBusinessList() {
        Wrapper<Business> wrapper = new EntityWrapper<>();
        List<Business> businessList = iBusinessService.selectList(wrapper);
        return businessList;
    }



}
