package com.zhidianfan.pig.yd.moduler.resv.thread;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.resv.service.VipStatisticsService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-01-24
 * @Modified By:
 */
@Slf4j
public class VipStatisticsThread implements Runnable {
    private VipStatisticsService vipStatisticsService;

    private Business business;

    private Collection<Integer> vipIds;


    public VipStatisticsThread(VipStatisticsService vipStatisticsService, Business business, Collection<Integer> vipIds) {
        this.vipStatisticsService = vipStatisticsService;
        this.business = business;
        this.vipIds = vipIds;
    }

    @Override
    public void run() {
        log.info("统计酒店:{}",business.getId());
        log.info("");
        this.vipStatisticsService.vipStatistics(business,vipIds);
    }
}
