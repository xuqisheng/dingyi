package com.zhidianfan.pig.yd.moduler.resv.controller;


import com.alibaba.fastjson.JSONObject;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.resv.service.SmsNumRemindService;
import com.zhidianfan.pig.yd.moduler.sms.dto.marketing.BusinessMarketingSmsTemplateDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huzp
 * @since 2019-06-13
 */
@Controller
@RequestMapping("/smsNumRemind")
public class SmsNumRemindController {


    @Autowired
    private SmsNumRemindService smsNumRemindService;


    /**
     * 查询短信是否提醒
     * @param businessId 酒店id
     * @param clientType  客户端id  1. 安卓电话机 2. 小程序
     * @return 操作结果
     */
    @GetMapping(value = "/remind")
    public ResponseEntity getRemind(@RequestParam("businessId") Integer businessId ,
                                    @RequestParam("clientType") Integer clientType) {


        JSONObject remind = smsNumRemindService.getRemind(businessId, clientType);

        return ResponseEntity.ok(remind);
    }




}

