package com.zhidianfan.pig.yd.moduler.resv.service;

import com.zhidianfan.pig.yd.moduler.common.service.IBusinessCustomerAnalysisInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sjl
 * 2019-05-29 10:43
 */

@Slf4j
@Service
public class BusinessCustomerAnalysisInfo {

//    1:活跃用户  2:沉睡用户  3:流失用户  4:意向用户   5:恶意用户  6:高价值用户  7:唤醒   8:新用户
    @Autowired
    private IBusinessCustomerAnalysisInfoService customerAnalysisInfoMapper;

    public void getAnalysisDetail() {

    }
}
