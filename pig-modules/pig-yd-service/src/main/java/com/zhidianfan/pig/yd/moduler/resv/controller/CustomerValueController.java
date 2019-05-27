package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.resv.service.CustomerValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author sjl
 * 2019-05-27 13:18
 */
@Slf4j
@RestController
@RequestMapping("/customervalue")
public class CustomerValueController {

    @Autowired
    private CustomerValueService customerValueService;

    /**
     * 执行任务的入口
     */
    @PostMapping("/task")
    public void getCustomerValue() {
        log.info("开始执行任务:{}", LocalDateTime.now());
        customerValueService.getCustomerValueBaseInfo();
        log.info("任务执行结束:{}", LocalDateTime.now());
    }

}
