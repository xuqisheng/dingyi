package com.zhidianfan.pig.yd.sms.web;

import com.zhidianfan.pig.yd.sms.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.sms.dao.entity.BaseSmsLog;
import com.zhidianfan.pig.yd.sms.dao.entity.SmsReply;
import com.zhidianfan.pig.yd.sms.dto.ClCallBackDTO;
import com.zhidianfan.pig.yd.sms.dto.LxCallBackDTO;
import com.zhidianfan.pig.yd.sms.dto.ReplyDTO;
import com.zhidianfan.pig.yd.sms.service.IBaseSmsLogService;
import com.zhidianfan.pig.yd.sms.service.ISmsReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URLDecoder;

/**
 * @Author: huzp
 * @Date: 2018/11/6 11:10
 * @DESCRIPTION 短信接口回调
 */
@RestController
@RequestMapping("/message")
public class SmsCallBackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsCallBackController.class);

    @Autowired
    private IBaseSmsLogService baseSmsLogService;

    @Autowired
    private ISmsReplyService smsReplyService;


    @Autowired
    private YdPropertites ydPropertites;

    /**
     * 创蓝回调短信更改状态
     *
     * @param clCallBackDTO
     */
    @GetMapping(value = "/clcallback/sync")
    public void clSyncMessage(ClCallBackDTO clCallBackDTO) {
        try {
            LOGGER.info(clCallBackDTO.toString());
            BaseSmsLog baseSmsLog = new BaseSmsLog();
            baseSmsLog.setMsgid(clCallBackDTO.getMsgid());

            if ("DELIVRD".equals(clCallBackDTO.getStatus())) {

                //成功短信加1,设置回调成功短信状态为2
                baseSmsLogService.updateCallBackSucStatus(baseSmsLog);
            } else {
                //失败短信+1,,设置回调成功短信状态为3
                baseSmsLogService.updateCallBackFailStatus(baseSmsLog);
            }

        } catch (Exception e) {
            LOGGER.info("创蓝回调短信更改状态接口异常:", e);
        }

    }

    /**
     * 创蓝回调短信插入回复短信
     *
     * @param replyDTO
     */
    @GetMapping(value = "/clcallback/reply")
    public void replyMessage(ReplyDTO replyDTO) {
        try {
            LOGGER.info(replyDTO.toString());
            SmsReply smsReply = new SmsReply();
            smsReply.setReplyContent(replyDTO.getMsg());
            smsReply.setPhone(replyDTO.getMobile());
            smsReply.setBusinessId(getBusinessId(replyDTO.getDestcode(), replyDTO.getSpCode()).intValue());
            smsReplyService.insert(smsReply);
            String msg = URLDecoder.decode(replyDTO.getMsg(), "UTF-8");
            LOGGER.info(msg);
        } catch (Exception e) {
            LOGGER.info("创蓝回调插入短信回复接口异常:", e);
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

    /**
     * 乐信通回调短信更改状态
     *
     * @param lxCallBackDTO
     */
    @RequestMapping(value = "/lxcallback/sync")
    public void lxSyncMessage(@Valid LxCallBackDTO lxCallBackDTO) {

        try {
            LOGGER.info(lxCallBackDTO.toString());
            BaseSmsLog baseSmsLog = new BaseSmsLog();

            baseSmsLog.setMsgid(lxCallBackDTO.getMsgID());
            if ("True".equals(lxCallBackDTO.getReportState())) {
                //成功短信加1,设置回调成功短信状态为2
                baseSmsLogService.updateCallBackSucStatus(baseSmsLog);
            } else {
                //失败短信+1,,设置回调成功短信状态为3
                baseSmsLogService.updateCallBackFailStatus(baseSmsLog);
            }
            LOGGER.info(lxCallBackDTO.toString());
            ClCallBackDTO clCallBackDTO = new ClCallBackDTO();
            clCallBackDTO.setStatus(lxCallBackDTO.getReportResultInfo());
            clCallBackDTO.setMsgid(lxCallBackDTO.getMsgID());
            if ("True".equals(lxCallBackDTO.getReportState())) {
                clCallBackDTO.setStatusDesc("短信发送成功");
            } else if ("False".equals(lxCallBackDTO.getReportState()) && "True".equals(lxCallBackDTO.getSendState())) {
                clCallBackDTO.setStatusDesc("短信提交成功，发送失败");
            } else if ("False".equals(lxCallBackDTO.getSendState())) {
//            smsLog.setStatusDesc("短信提交失败");
                clCallBackDTO.setStatusDesc("");
            }
            clCallBackDTO.setReportTime(lxCallBackDTO.getReportTime());

            String status = clCallBackDTO.getStatus();
            String statusDesc = clCallBackDTO.getStatusDesc();
            String msgid = clCallBackDTO.getMsgid();
            String reportTime = clCallBackDTO.getReportTime();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity httpEntity = new HttpEntity(clCallBackDTO, httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(ydPropertites.getSms().getUrl() + "/sync?status=" + status + "&statusDesc=" + statusDesc + "&msgid=" + msgid + "&reportTime=" + reportTime, HttpMethod.GET, httpEntity, String.class);

        } catch (Exception e) {
            LOGGER.info("乐信回调短信更改状态接口异常:", e);
        }

    }

}
