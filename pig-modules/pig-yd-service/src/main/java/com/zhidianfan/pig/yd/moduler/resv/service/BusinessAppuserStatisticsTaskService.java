package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hzp
 * @Date: 2019-05-05 13:21
 * @Description:
 */
@Service
public class BusinessAppuserStatisticsTaskService {


    @Autowired
    private IBusinessService businessService;

    /**
     * 统计上个月营销经理数据
     */
    @Async
    public void BusinessAppuserStatistics(String lastYearMonth, String yearMonth) {


        List<Business> businessList = businessService.selectList(
                new EntityWrapper<Business>().eq("status", 1));

        Map param = new HashMap();
        param.put("lastYearMonth", lastYearMonth);
        param.put("yearMonth", yearMonth);
        // 清空上个月的统计
        for (Business business : businessList){
            int businessId =  business.getId();
            param.put("businessId", businessId);


//            batchDao.clearTodayStatistics(param);
//            // 插入上月营销经理 统计
//            batchDao.insertBusinessAppuserStatistics(param);
        }
    }
}
