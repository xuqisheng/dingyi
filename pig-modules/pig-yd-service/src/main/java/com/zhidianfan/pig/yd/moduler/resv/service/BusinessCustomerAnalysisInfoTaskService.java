package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysisDetail;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysisInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessCustomerAnalysisInfoTask;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessCustomerAnalysisInfoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sjl
 * 2019-06-23 12:26
 */
@Service
public class BusinessCustomerAnalysisInfoTaskService {

    @Autowired
    private IBusinessCustomerAnalysisInfoTaskService infoTaskService;

    public List<BusinessCustomerAnalysisInfoTask> getTaskList() {
        Wrapper<BusinessCustomerAnalysisInfoTask> wrapper = new EntityWrapper<>();
        wrapper.eq("use_tag", 0);
        return infoTaskService.selectList(wrapper);
    }


    public List<BusinessCustomerAnalysisInfoTask> getTaskList(Integer businessId) {
        Wrapper<BusinessCustomerAnalysisInfoTask> wrapper = new EntityWrapper<>();
        wrapper.eq("use_tag", 0);
        wrapper.eq("business_id", businessId);
//        wrapper.ge("date", startDate);
//        wrapper.le("date", endDate);
        return infoTaskService.selectList(wrapper);
    }

    public List<BusinessCustomerAnalysisInfoTask> getTaskList(List<Integer> businessIds) {
        Wrapper<BusinessCustomerAnalysisInfoTask> wrapper = new EntityWrapper<>();
        wrapper.eq("use_tag", 0);
        wrapper.in("business_id", businessIds);
        return infoTaskService.selectList(wrapper);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateUseTag(List<BusinessCustomerAnalysisInfoTask> businessCustomerAnalysisInfoTaskList) {
        Wrapper<BusinessCustomerAnalysisInfoTask> wrapper = new EntityWrapper<>();
        List<BusinessCustomerAnalysisInfoTask> infoTasks = businessCustomerAnalysisInfoTaskList.stream()
                .parallel()
                .peek(task -> task.setUseTag(1))
                .collect(Collectors.toList());
        infoTaskService.updateBatchById(infoTasks, 500);
    }

//    public void updateUseTag(List<Integer> businessList) {
//        Wrapper<BusinessCustomerAnalysisInfoTask> wrapper = new EntityWrapper<>();
//        infoTaskService.update()
//    }

}
