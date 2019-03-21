package com.zhidianfan.pig.push.controller;

import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.dto.WxBatchPushDTO;
import com.zhidianfan.pig.common.dto.WxSinglePushDTO;
import com.zhidianfan.pig.push.service.impl.WxPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/6
 * @Modified By:
 */
@RestController
@RequestMapping("/wx")
@Slf4j
public class WxPushController {
    @Autowired
    private WxPushService pushService;

    /**
     * 推送给单个微信用户
     * @param pushDTO
     * @return
     */
    @PostMapping("/push/single")
    public ResponseEntity pushToSingle(@Valid @RequestBody WxSinglePushDTO pushDTO){
        boolean b = pushService.pushToSingle(pushDTO.getOpenId(), pushDTO.getTemplateId(), pushDTO.getUrl(), pushDTO.getParams());
        return ResponseEntity.ok(b?new SuccessTip(200,"推送成功"):new ErrorTip(400,"推送失败"));
    }

    /**
     * 推送给多个微信用户
     * @param pushDTO
     * @return
     */
    @PostMapping("/push/batch")
    public ResponseEntity pushToBatch(@Valid @RequestBody WxBatchPushDTO pushDTO){
        pushDTO.getParams().forEach((openId,params)->{
            pushService.pushToSingle(openId, pushDTO.getTemplateId(), pushDTO.getUrl(), params);
        });
        return ResponseEntity.ok(new SuccessTip(200,"推送成功"));
    }




}
