package com.zhidianfan.pig.yd.moduler.sms.controller;

import com.zhidianfan.pig.yd.moduler.sms.dto.CallBackDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.ReplyDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSLogDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSReplyDTO;
import com.zhidianfan.pig.yd.moduler.sms.service.SMSLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

/**
 * 创蓝回馈短信接口
 *
 * @author wangyz
 * @version v 0.1 2018/4/9 下午2:01 wangyz Exp $
 */
@RestController
@RequestMapping("///message/callback")
public class MessageCallBackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCallBackController.class);

    @Autowired
    private SMSLogService logService;

    /**
     * 创蓝回调短信更改状态
     *
     * @param callBackDTO
     */
    @GetMapping(value = "/sync")
    public void syncMessage(CallBackDTO callBackDTO) {
        LOGGER.info(callBackDTO.toString());
        SMSLogDTO log = new SMSLogDTO();
        log.setMsgid(callBackDTO.getMsgid());
        log.setStatus(callBackDTO.getStatus());
        log.setStatusDesc(callBackDTO.getStatusDesc());
        log.setReportTime(callBackDTO.getReportTime());
        logService.updateSMSLog(log);
    }

    /**
     * 创蓝回调短信插入回复短信
     *
     * @param replyDTO
     */
    @GetMapping(value = "/reply")
    public void replyMessage(ReplyDTO replyDTO) {
        try {
            LOGGER.info(replyDTO.toString());
            SMSReplyDTO smsReplyDTO = new SMSReplyDTO();
            smsReplyDTO.setReplyContent(replyDTO.getMsg());
            smsReplyDTO.setPhone(replyDTO.getMobile());
            smsReplyDTO.setBusinessId(getBusinessId(replyDTO.getDestcode(), replyDTO.getSpCode()));
            logService.insertSMSReply(smsReplyDTO);
            String msg = URLDecoder.decode(replyDTO.getMsg(), "UTF-8");
            LOGGER.info(msg);
        } catch (Exception e) {
            LOGGER.info("创蓝回调接口异常:", e);
        }
    }

    private Long getBusinessId(String destcode, String spCode) {
        StringBuilder sb = new StringBuilder();
        char[] list = destcode.toCharArray();
        for (int i = spCode.length(); i < list.length; i++) {
            sb.append(list[i]);
        }
        return Long.valueOf(sb.toString());
    }
}