package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipStatistics;
import com.zhidianfan.pig.yd.moduler.resv.service.VipStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Bazinga
 * @since 2018-11-15
 */
@Controller
@RequestMapping("/vipStatistics")
public class VipStatisticsController {

    @Autowired
    private VipStatisticsService vipStatisticsService;

    /**
     * 获取一个用户的统计信息（ 预定统计、就餐统计、最近就餐日期 ）
     * @param businessId
     * @param vipPhone
     * @return
     */
    @GetMapping(value = "/statistics", params = {"vipId"})
    public ResponseEntity getVipstatistics(@RequestParam String vipId) {
        VipStatistics vipStatistics = vipStatisticsService.getVipStatistics(vipId);
        return ResponseEntity.ok(vipStatistics);
    }
}

