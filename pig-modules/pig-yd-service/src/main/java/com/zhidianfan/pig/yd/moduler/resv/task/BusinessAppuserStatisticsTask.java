package com.zhidianfan.pig.yd.moduler.resv.task;

import com.netflix.discovery.converters.Auto;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessAppuserStatisticsTaskService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hzp
 * @Date: 2019-05-05 13:16
 * @Description: 月度营销经理统计,去除存储过程
 *
 */
@RestController
@RequestMapping("/businessappuserstatisticstask")
public class BusinessAppuserStatisticsTask {


    @Autowired
    private BusinessAppuserStatisticsTaskService businessAppuserStatisticsTaskService;

    @PostMapping("/update")
    public ResponseEntity updateOrderStatusNew(String lastYearMonth, String yearMonth) {


        businessAppuserStatisticsTaskService.BusinessAppuserStatistics(lastYearMonth,yearMonth);


        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

}
