package com.zhidianfan.pig.yd.moduler.resv.thread;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.resv.service.OverTimeOrderService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * @Author: hzp
 * @Date: 2019-05-24 10:35
 * @Description:
 */
@Slf4j
public class OverTimeOrderThread implements Runnable{

    private OverTimeOrderService overTimeOrderService;

    private List<Business> businessList;


    public OverTimeOrderThread(OverTimeOrderService overTimeOrderService, List<Business> businessList) {
        this.overTimeOrderService = overTimeOrderService;
        this.businessList = businessList;
    }

    @Override
    public void run() {

        log.info("统计酒店数量:{}",businessList.size());

        this.overTimeOrderService.countOverTimeOrderAndPushMessage(businessList);

    }
}
