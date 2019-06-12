package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AndroidUserInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SellerUser;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsValidate;
import com.zhidianfan.pig.yd.moduler.common.service.IAndroidUserInfoService;
import com.zhidianfan.pig.yd.moduler.common.service.ISellerUserService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsValidateService;
import com.zhidianfan.pig.yd.moduler.manage.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.manage.dto.TipCommon;
import com.zhidianfan.pig.yd.moduler.manage.feign.AuthFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.SmsValidateService;
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
@RequestMapping("/sms/validate")
public class SmsValidateController {
    @Autowired
    private ISellerUserService sellerUserService;

    @Autowired
    private SmsValidateService smsValidateService;

    @Autowired
    private ISmsValidateService validateService;

    @Autowired
    private AuthFeign authFeign;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/send")
    public ResponseEntity sendValidate(String username){
//        SellerUser userInfo = sellerUserService.selectOne(new EntityWrapper<SellerUser>().eq("login_name", username));
        TipCommon tipCommon = authFeign.findUserPhone(username, "WEB_MANAGER");

        log.info("tipCommon:{}",tipCommon);
        if(tipCommon.getCode() != 200){
            tipCommon.setCode(500);
            tipCommon.setMsg("用户该客户端无法登录");
            return ResponseEntity.badRequest().body(tipCommon);
        }

//        if(null == userInfo){
//            log.debug("用户名:{} 不存在", username);
//            tipCommon.setCode(500);
//            tipCommon.setMsg("用户名不存在");
//            return ResponseEntity.badRequest().body(tipCommon);
//        }
//
//        if(!StringUtils.equals(userInfo.getStatus(),"1")){
//            log.debug("用户名:{} 状态:{} 不可用",userInfo.getLoginName(),userInfo.getStatus());
//            tipCommon.setCode(500);
//            tipCommon.setMsg("用户状态不可用");
//            return ResponseEntity.badRequest().body(tipCommon);
//        }
        Random random = new Random();

        String code = "";
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }

        LoginMessageVo loginMessageVo = new LoginMessageVo();
        loginMessageVo.setBusinessId(30L);
        loginMessageVo.setCode(code);
        loginMessageVo.setPhone(Long.valueOf(tipCommon.getMsg()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginMessageVo> httpEntity = new HttpEntity<>(loginMessageVo, httpHeaders);
        if(!smsValidateService.checkMobileSendTime(tipCommon.getMsg())){
            tipCommon.setCode(500);
            tipCommon.setMsg("短信发送过于频繁，每分钟只能发送一次");
            return ResponseEntity.badRequest().body(tipCommon);
        }
        ResponseEntity<Map> exchange = null;
        try {
            exchange = restTemplate.exchange("http://sms.zhidianfan.com/message/sendLoginMessage", HttpMethod.POST, httpEntity, Map.class);
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
            smsValidate.setPhone(tipCommon.getMsg());
            validateService.insert(smsValidate);
        }

        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }
}
