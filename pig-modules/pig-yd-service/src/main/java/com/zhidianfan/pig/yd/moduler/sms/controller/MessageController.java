package com.zhidianfan.pig.yd.moduler.sms.controller;

import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.sms.bo.message.FailStatusBO;
import com.zhidianfan.pig.yd.moduler.sms.bo.message.MessageResultBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.LoginMessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.MessageDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.SuccessSms;
import com.zhidianfan.pig.yd.moduler.sms.service.MessageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author Conan
 * @Description 短信发送控制层
 * @Date: 2018/9/12 0012 下午 4:31
 * @Modified By:
 */
@RestController
@RequestMapping("/message")
public class MessageController{

    Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    /**
     * 发送短信
     *
     * @param messageDTO
     * @return
     */
    @PostMapping(value = "/sendMessage")
    public ResponseEntity sendMessage(@RequestBody MessageDTO messageDTO, BindingResult error) {
        LOGGER.info("接收发送短信请求：" + messageDTO.toString());
        //Step 1请求参数校验
        FailStatusBO failStatusBO = getFailStatusBO(messageDTO, error);
        if (failStatusBO!=null){
            return ResponseEntity.badRequest().body(failStatusBO);
        }

        //Step 2 短SUCCESS_SMS.信发送
        MessageResultBO result = messageService.sendMessage(messageDTO);
//        WebResult<Boolean> webResult;
//        if (ChuangLanUtils.FAIL_STATUS.equals(result.getStatus()))
//            webResult = WebResultUtils.newSuccessWebResult(Boolean.FALSE);
//        else
//            webResult = WebResultUtils.newSuccessWebResult(Boolean.TRUE);
        Map<String, Object> param = Maps.newHashMap();
        param.put("data", result);
        SuccessSms successSms = new SuccessSms();
        successSms.setExtDate(param);
//        webResult.setExtDate(param);
        return ResponseEntity.ok(successSms);
    }




    /**
     * 发送登录验证短信
     *
     * @param loginMessageDTO
     * @return
     */
    @PostMapping(value = "/sendLoginMessage")
    public ResponseEntity sendLoginMessage(@RequestBody LoginMessageDTO loginMessageDTO) {
        LOGGER.info("接收发送登录验证短信请求：" + loginMessageDTO.toString());
        MessageResultBO result = messageService.sendLoginMessage(loginMessageDTO);
        Map<String, Object> param = Maps.newHashMap();
        param.put("data", result);
        SuccessSms successSms = new SuccessSms();
        successSms.setExtDate(param);
        return ResponseEntity.ok(successSms);
    }

    /**
     * 发送宴会短信
     *
     * @param messageDTO
     * @return
     */
    @PostMapping(value = "/sendMeetingSMS")
    public ResponseEntity sendMeetingSMS(@RequestBody MessageDTO messageDTO, BindingResult error) {
        LOGGER.info("接收发送宴会短信请求：" + messageDTO.toString());
        //Step 1请求参数校验
        FailStatusBO failStatusBO = getFailStatusBO(messageDTO, error);
        if (failStatusBO!=null){
            return ResponseEntity.badRequest().body(failStatusBO);
        }
        //Step 2 短信发送
        MessageResultBO result = messageService.sendMeetingSMS(messageDTO);
        Map<String, Object> param = Maps.newHashMap();
        param.put("data", result);
        SuccessSms successSms = new SuccessSms();
        successSms.setExtDate(param);
        return ResponseEntity.ok(successSms);
    }



    /**
     * 判断请求校验情况
     * @param messageDTO
     * @param error
     * @return
     */
    private FailStatusBO getFailStatusBO(MessageDTO messageDTO, BindingResult error) {
        FailStatusBO failStatusBO = null;
        if (error.hasErrors()) {
            failStatusBO = messageService.failSign(-200,"请求参数缺失");
        }
        if (messageDTO.getBusinessId() == null) {
            failStatusBO = messageService.failSign(-200,"请求参数缺失:酒店id缺失");
        }
        if (messageDTO.getTemplateType() == null) {
            failStatusBO = messageService.failSign(-200,"请求参数缺失:模板类型缺失");
        }
        if (StringUtils.isEmpty(messageDTO.getPhone())) {
            failStatusBO = messageService.failSign(-200,"请求参数缺失：手机号码缺失");
        }
        if (StringUtils.isEmpty(messageDTO.getResvOrder())) {
            failStatusBO = messageService.failSign(-200,"请求参数缺失：订单号缺失");
        }
        return failStatusBO;
    }
}
