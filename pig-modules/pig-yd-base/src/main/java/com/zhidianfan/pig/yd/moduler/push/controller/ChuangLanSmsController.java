package com.zhidianfan.pig.yd.moduler.push.controller;

import com.google.common.base.Joiner;
import com.zhidianfan.pig.yd.moduler.push.dto.ClMsgParam;
import com.zhidianfan.pig.yd.moduler.push.dto.SmsSendResDTO;
import com.zhidianfan.pig.yd.moduler.push.service.ChunagLanSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 创蓝短信服务
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/30
 * @Modified By:
 */
@RestController
@RequestMapping("/clsms")
public class ChuangLanSmsController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChunagLanSmsService chunagLanSmsService;

    /**
     * 创蓝单笔普通短信发送
     *
     * @param phone
     * @param msg
     * @return
     */
    @PostMapping(value = "/send", params = {"phone", "msg"})
    public ResponseEntity sendNormalMsg(@RequestParam String phone
            , @RequestParam String msg) {
        log.info("向 {} 发送短信 {} ", phone, msg);
        long smsId = chunagLanSmsService.insertSmsLog(phone, msg).getId();


        SmsSendResDTO smsSendResDTO = chunagLanSmsService.sendMsg(phone, msg, "", smsId);
        return ResponseEntity.ok(smsSendResDTO);
    }

    /**
     * 创蓝批量普通短信发送
     *
     * @param clMsgParam
     * @return
     */
    @PostMapping(value = "/sendLot")
    public ResponseEntity sendBatchNormalMsg(@RequestBody ClMsgParam clMsgParam) {
        List<String> phones = clMsgParam.getPhone();
        String phone = Joiner.on(",").join(phones);
        String msg = clMsgParam.getMsg();
        log.info("向 {} 发送短信 {} ", phone, msg);
        long smsId = chunagLanSmsService.insertSmsLog(phone, msg).getId();

        SmsSendResDTO smsSendResDTO = chunagLanSmsService.sendMsg(phone, msg, "", smsId);
        return ResponseEntity.ok(smsSendResDTO);
    }

    /**
     * 创蓝单笔营销短信发送
     *
     * @param phone
     * @param msg
     * @return
     */
    @PostMapping(value = "/send/v1", params = {"phone", "msg"})
    public ResponseEntity sendmarkMsg(@RequestParam String phone
            , @RequestParam String msg) {
        log.info("向 {} 发送短信 {} ", phone, msg);
        long smsId = chunagLanSmsService.insertSmsLog(phone, msg).getId();


        SmsSendResDTO smsSendResDTO = chunagLanSmsService.sendMsg(phone, msg, "YX", smsId);
        return ResponseEntity.ok(smsSendResDTO);
    }

    /**
     * 创蓝批量营销短信发送
     *
     * @param clMsgParam
     * @return
     */
    @PostMapping(value = "/sendLot/v1")
    public ResponseEntity sendBatchmarkMsg(@RequestBody ClMsgParam clMsgParam) {
        List<String> phones = clMsgParam.getPhone();
        String phone = Joiner.on(",").join(phones);
        String msg = clMsgParam.getMsg();
        log.info("向 {} 发送短信 {} ", phone, msg);
        long smsId = chunagLanSmsService.insertSmsLog(phone, msg).getId();

        SmsSendResDTO smsSendResDTO = chunagLanSmsService.sendMsg(phone, msg, "YX", smsId);
        return ResponseEntity.ok(smsSendResDTO);
    }
}
