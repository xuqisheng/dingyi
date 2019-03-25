package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AndroidUserInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsValidate;
import com.zhidianfan.pig.yd.moduler.common.service.IAndroidUserInfoService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsValidateService;
import com.zhidianfan.pig.yd.moduler.manage.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.manage.dto.TipCommon;
import com.zhidianfan.pig.yd.moduler.resv.service.SmsService;
import com.zhidianfan.pig.yd.moduler.resv.vo.LoginMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-03-25
 * @Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private IAndroidUserInfoService androidUserInfoService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ISmsValidateService smsValidateService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/send/validate")
    public ResponseEntity sendValidate(String username){
        AndroidUserInfo userInfo = androidUserInfoService.selectOne(new EntityWrapper<AndroidUserInfo>().eq("login_name", username));
        TipCommon tipCommon = new TipCommon();
        if(null == userInfo){
            log.debug("用户名:{} 不存在", username);
            tipCommon.setCode(500);
            tipCommon.setMsg("用户名不存在");
            return ResponseEntity.badRequest().body(tipCommon);
        }

        if(!StringUtils.equals(userInfo.getStatus(),"1")){
            log.debug("用户名:{} 状态:{} 不可用",userInfo.getLoginName(),userInfo.getStatus());
            tipCommon.setCode(500);
            tipCommon.setMsg("用户状态不可用");
            return ResponseEntity.badRequest().body(tipCommon);
        }
        Random random = new Random();

        String code = "";
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }

        LoginMessageVo loginMessageVo = new LoginMessageVo();
        loginMessageVo.setBusinessId(30L);
        loginMessageVo.setCode(code);
        loginMessageVo.setPhone(Long.valueOf(userInfo.getAppUserPhone()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginMessageVo> httpEntity = new HttpEntity<>(loginMessageVo, httpHeaders);
        if(!smsService.checkMobileSendTime(userInfo.getAppUserPhone())){
            tipCommon.setCode(500);
            tipCommon.setMsg("短信发送过于频繁，每分钟只能发送一次");
            return ResponseEntity.badRequest().body(tipCommon);
        }
        ResponseEntity<Map> exchange = null;
        try {
            exchange = restTemplate.exchange("http://server4.zhidianfan.com:9095/message/sendLoginMessage", HttpMethod.POST, httpEntity, Map.class);
        } catch (RestClientException e) {
            log.error("短信发送异常");
            tipCommon.setCode(500);
            tipCommon.setMsg("网络繁忙，稍后重试");
            return ResponseEntity.badRequest().body(tipCommon);
        }
        Map body = exchange.getBody();
        log.info("短信发送结果:{}", body);
        boolean data = (boolean)body.get("data");
        if (data) {
            SmsValidate smsValidate = new SmsValidate();
            smsValidate.setCode(code);
            smsValidate.setIsUse(0);
            smsValidate.setBusinessId(30);
            smsValidate.setPhone(userInfo.getAppUserPhone());
            smsValidateService.insert(smsValidate);
        }

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }
}
