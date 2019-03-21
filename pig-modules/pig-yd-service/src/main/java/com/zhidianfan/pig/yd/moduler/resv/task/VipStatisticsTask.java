package com.zhidianfan.pig.yd.moduler.resv.task;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.dto.VipOrderDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.VipStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/15
 * @Modified By:
 */
@RestController
@RequestMapping("/statistics")
@Slf4j
public class VipStatisticsTask {
    @Autowired
    private VipStatisticsService vipStatisticsService;

    @GetMapping("/business")
    public ResponseEntity businessVipStatistics() {
        vipStatisticsService.statisticsAllBusinessVip();
        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }


    @GetMapping("/vip/order")
    public ResponseEntity vipOrderStatistics(VipOrderDTO vipOrderDTO) {
        vipStatisticsService.vipOrderStatistics(vipOrderDTO);
        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }
}
