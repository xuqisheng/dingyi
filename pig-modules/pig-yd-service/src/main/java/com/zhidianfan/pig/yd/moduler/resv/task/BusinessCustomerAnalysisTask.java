package com.zhidianfan.pig.yd.moduler.resv.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysisInfoTask;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessCustomerAnalysisInfoTaskService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

    // @Scheduled(cron = "0 0 1 1 1/1 ?")
    public void generatorTaskList(LocalDate resvDate) {

        List<Business> businessList = getBusinessList();
        List<BusinessCustomerAnalysisInfoTask> list = new ArrayList<>(businessList.size());
        for (Business business : businessList) {
            BusinessCustomerAnalysisInfoTask task = new BusinessCustomerAnalysisInfoTask();
            task.setBusinessId(business.getId());
            // LocalDate now = LocalDate.now();
            String date = DateTimeFormatter.ofPattern("yyyy-MM").format(resvDate);
            task.setDate(date);
            task.setUseTag(0);
            task.setUpdateTime(LocalDateTime.now());
            list.add(task);
        }
        iTaskService.insertBatch(list, 500);
    }

    public List<Business> getBusinessList() {
        Wrapper<Business> wrapper = new EntityWrapper<>();
        List<Business> businessList = iBusinessService.selectList(wrapper);
        return businessList;
    }

}
