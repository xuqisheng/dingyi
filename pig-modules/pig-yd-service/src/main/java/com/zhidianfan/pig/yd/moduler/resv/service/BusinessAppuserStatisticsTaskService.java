package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessAppuserStatisticsService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BusinessAppuserStatisticsTaskService {


    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IBusinessAppuserStatisticsService iBusinessAppuserStatisticsService;

    /**
     * 统计上个月营销经理数据
     */
    @Async
    public void BusinessAppuserStatistics(String lastYearMonth, String yearMonth) {

        long l = System.currentTimeMillis();
        log.info("任务开始 ---" + l);

        List<Business> businessList = businessService.selectList(
                new EntityWrapper<Business>().eq("status", 1));

        // 清空上个月的统计
        for (Business business : businessList) {
            int businessId = business.getId();

            try {

                //删除这个酒店lastYearMonth 这个月份的营销经理数据
                iBusinessAppuserStatisticsService.clearTodayStatistics(businessId, lastYearMonth);


                //删除临时表
                iBusinessAppuserStatisticsService.dropTemporaryTable();

                System.out.println("酒店id ======" + businessId);


                //统计
                //1. 创建 临时表t_business_appuser_statistics_temporary
                iBusinessAppuserStatisticsService.createTemporaryTable(businessId, lastYearMonth, yearMonth);


                //2.  插入营销经理数据
                iBusinessAppuserStatisticsService.insertAppuserStatistics(businessId, lastYearMonth, yearMonth);

                //3. 插入预订台数据
                iBusinessAppuserStatisticsService.insertPadStatistics(businessId, lastYearMonth, yearMonth);
            } catch (Exception e) {
                log.error("更新酒店营销经理数据错误:" + businessId);
            }
        }

        long currentTimeMillis = System.currentTimeMillis();
        log.info("----任务结束,总耗时-----" + (currentTimeMillis - l) + "");

    }
}
