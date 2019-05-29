package com.zhidianfan.pig.yd.moduler.resv.task;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.service.OverTimeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hzp
 * @Date: 2019-05-24 09:59
 * @Description:
 */
@RestController
@RequestMapping("/overtimeorder")
@Slf4j
public class OverTimeOrderTask {


    @Autowired
    private OverTimeOrderService overTimeOrderService;

    @GetMapping("/statistics")
    public ResponseEntity businessOverTimeOrderStatistics() {
        overTimeOrderService.statisticsOverTimeOrder();
        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }



}
