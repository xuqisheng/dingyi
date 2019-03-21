package com.zhidianfan.pig.yd.moduler.group.controller;


import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.group.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  集团统计
 * </p>
 *
 * @author ljh
 * @since 2019-01-27
 */
@Controller
@RequestMapping("/group/statistics")
public class StatisticsController {


    @Resource
    private StatisticsService statisticsService;

    @PostMapping("salesman/order")
    public ResponseEntity SalesmanOrder(Integer type,Date startTime){

         statisticsService.salesmanOrder( type,startTime);
        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }




}

