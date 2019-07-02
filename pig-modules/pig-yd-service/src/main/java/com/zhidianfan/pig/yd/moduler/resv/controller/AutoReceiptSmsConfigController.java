package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.zhidianfan.pig.yd.moduler.common.dao.entity.AutoReceiptSmsConfig;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.dto.AutoReceiptSmsConfigDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.AutoReceiptSmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 自动接单自动发送短信配置
 * </p>
 *
 * @author huzp
 * @since 2019-06-13
 */
@Controller
@RequestMapping("/autoReceiptSmsConfig")
public class AutoReceiptSmsConfigController {

    @Autowired
    AutoReceiptSmsConfigService autoReceiptSmsConfigService;


    /**
     * 修改或者新增酒店自动接单短信配置
     *
     * @return 操作结果
     */
    @PostMapping("/editinfo")
    public ResponseEntity editAutoReceiptSmsConfig(@RequestBody AutoReceiptSmsConfigDTO
                                                               autoReceiptSmsConfigDTO) {

        boolean b = autoReceiptSmsConfigService.editAutoReceiptSmsConfig(autoReceiptSmsConfigDTO);

        Tip tip = b ? SuccessTip.SUCCESS_TIP : ErrorTip.ERROR_TIP;

        return ResponseEntity.ok(tip);
    }


    @GetMapping("/getinfo")
    public ResponseEntity getAutoReceiptSmsConfig(@RequestParam("id") Integer id) {

        AutoReceiptSmsConfig autoReceiptSmsConfig = autoReceiptSmsConfigService.getAutoReceiptSmsConfig(id);


        return ResponseEntity.ok(autoReceiptSmsConfig);
    }



}

