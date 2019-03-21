package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.zhidianfan.pig.yd.moduler.common.dao.entity.AutoReceiptConfig;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.service.AutoReceiptConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hzp
 * @since 2019-01-28
 */
@Controller
@RequestMapping("/autoreceiptconfig")
public class AutoReceiptConfigController {


    @Autowired
    AutoReceiptConfigService autoReceiptConfigService;


    /**
     * 修改或者新增酒店自动接单配置
     * @return
     */
    @PostMapping("/info")
    public ResponseEntity editAutoReceiptConfig(@RequestBody AutoReceiptConfig autoReceiptConfig){

        Tip tip = autoReceiptConfigService.editAutoReceiptConfig(autoReceiptConfig);

        return ResponseEntity.ok(tip);
    }


    @GetMapping("/info")
    public ResponseEntity getAutoReceiptConfig(@RequestParam("businessid") Integer bId){

        //
        AutoReceiptConfig autoReceiptConfig = autoReceiptConfigService.getAutoReceiptConfig(bId);

        return ResponseEntity.ok(autoReceiptConfig);
    }

}

