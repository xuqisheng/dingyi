package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.alibaba.fastjson.JSONObject;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessOrderStatisticsDay;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.dto.BusinessMonthDataDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ConsumptionFrequencyDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.RepeatVipDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.BusinessOrderStatisticsDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2018-12-20
 */
@Controller
@RequestMapping("/orderdatadays")
@Api(value = "数据中心")
public class BusinessOrderStatisticsDayController {

    @Autowired
    BusinessOrderStatisticsDayService businessOrderStatisticsDayService;

    @ApiOperation(value = "统计酒店数据")
    @PostMapping(value = "/statistics")
    public ResponseEntity statisticsOrderData() {

        businessOrderStatisticsDayService.statisticsData();

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    @ApiOperation(value = "获取某个酒店某天数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "businessid", value = "酒店id", dataType = "integer"),
            @ApiImplicitParam(paramType = "query", name = "caldate", value = "数据时间格式为 %Y-%M-%D", dataType = "String")
    })
    @GetMapping(value = "/businessdaydata")
    public ResponseEntity getBusinessDaydata(@RequestParam("businessid") Integer businessId,
                                             @RequestParam("caldate") String calDate) {

        //获取酒店该天订单分布
        List<Map<String, Integer>> orderDistribution  = businessOrderStatisticsDayService.getOrderDistribution(businessId,calDate);

        List<BusinessOrderStatisticsDay> businessOrderStatisticsDayData = businessOrderStatisticsDayService.getBusinessDaydata(businessId, calDate);

        JSONObject reslut = new JSONObject();
        reslut.put("orderDistribution",orderDistribution);
        reslut.put("businessOrderStatisticsDayData",businessOrderStatisticsDayData);

        return ResponseEntity.ok(reslut);
    }


    @ApiOperation(value = "获取某个酒店长期数据 结账桌数,新增客户数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "businessid", value = "酒店id", dataType = "integer"),
            @ApiImplicitParam(paramType = "query", name = "monthnum", value = "最近几个月", dataType = "integer")
    })
    @GetMapping(value = "/businessmonthdata")
    public ResponseEntity getBusinessMonthdata(@RequestParam("businessid") Integer businessId,
                                               @RequestParam("monthnum") Integer monthNum) {

        List<BusinessMonthDataDTO> businessOrderStatisticsMonthData = businessOrderStatisticsDayService.getBusinessMonthData(businessId, monthNum);

        return ResponseEntity.ok(businessOrderStatisticsMonthData);
    }


    @ApiOperation(value = "获取某个酒店长期数据 消费频次")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "businessid", value = "酒店id", dataType = "integer"),
            @ApiImplicitParam(paramType = "query", name = "monthnum", value = "相差月数", dataType = "integer")
    })
    @GetMapping(value = "/businessmonthdata/consumptionfrequency")
    public ResponseEntity getConsumptionFrequency(@RequestParam("businessid") Integer businessId,
                                               @RequestParam("monthnum") Integer monthNum) {

        List<ConsumptionFrequencyDTO> connsumptionFrequencyList = businessOrderStatisticsDayService.getConsumptionFrequency(businessId, monthNum);

        return ResponseEntity.ok(connsumptionFrequencyList);
    }

    /**
     * 复购客户统计
     */
    @ApiOperation(value = "获取某个酒店长期数据 复购客户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "businessid", value = "酒店id", dataType = "integer"),
            @ApiImplicitParam(paramType = "query", name = "monthnum", value = "相差月数", dataType = "integer")
    })
    @GetMapping(value = "/businessmonthdata/repeatvip")
    public ResponseEntity getRepeatVipByMonth   (@RequestParam("businessid") Integer businessId,
                                                  @RequestParam("monthnum") Integer monthNum) {

        List<RepeatVipDTO> repeatVipDTOS = businessOrderStatisticsDayService.getRepeatVipByMonth(businessId, monthNum);

        return ResponseEntity.ok(repeatVipDTOS);
    }


}

