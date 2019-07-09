package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.zhidianfan.pig.yd.moduler.resv.dto.CommonRes;
import com.zhidianfan.pig.yd.moduler.resv.task.BusinessCustomerAnalysisTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 客户分析酒店记录表 前端控制器
 * </p>
 *
 * @author qqx
 * @since 2019-06-23
 */
@RestController
@RequestMapping("/businessCustomerAnalysisInfoTask")
public class BusinessCustomerAnalysisInfoTaskController {

    @Autowired
    private BusinessCustomerAnalysisTask task;

    @GetMapping("/task")
    public ResponseEntity task() {
        task.generatorTaskList();
        return ResponseEntity.ok(CommonRes.SUCCESS);

    }

    @GetMapping("/currentTask")
    public ResponseEntity currentTask() {
        task.generatorTask();
        return ResponseEntity.ok(CommonRes.SUCCESS);
    }

}

