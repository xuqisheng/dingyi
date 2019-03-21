package com.zhidianfan.pig.yd.moduler.push.service;

import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BaseSmsLog;
import com.zhidianfan.pig.yd.moduler.common.service.IBaseSmsLogService;
import com.zhidianfan.pig.yd.moduler.push.dto.ClMsgContent;
import com.zhidianfan.pig.yd.moduler.push.dto.SmsSendResDTO;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/30
 * @Modified By:
 */
@Service
public class ChunagLanSmsService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private YdPropertites ydPropertites;
    @Autowired
    private IBaseSmsLogService smsLogService;

    /**
     * 短信发送
     *
     * @param phone
     * @param msg
     * @return
     */
    public SmsSendResDTO sendMsg(String phone, String msg, String type, long smsId) {
        ClMsgContent clMsgContent = createMsgContent(phone, msg, type);
        String jsonStr = JsonUtils.obj2Json(clMsgContent);
        String url = ydPropertites.getCl().getUrl();

        BaseSmsLog baseSmsLog = new BaseSmsLog();
        baseSmsLog.setId(smsId);
        baseSmsLog.setStatus(1);

//        Tip tip = SuccessTip.SUCCESS_TIP;
        SmsSendResDTO smsSendResDTO = new SmsSendResDTO();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");

        HttpEntity httpEntity = new HttpEntity(jsonStr, httpHeaders);
        ResponseEntity<Map> responseEntity;
        String res = "";
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            Map<String, Object> resMap = responseEntity.getBody();

            res = JsonUtils.obj2Json(resMap);
            smsSendResDTO.setCode(MapUtils.getString(resMap, "code"));
            smsSendResDTO.setMsgId(MapUtils.getLong(resMap, "msgId"));
            smsSendResDTO.setStatus("SUCCESS");
            smsSendResDTO.setTime(MapUtils.getString(resMap, "time"));
            smsSendResDTO.setErrorMsg(MapUtils.getString(resMap, "errorMsg"));

            if (StringUtils.isNotEmpty((CharSequence) resMap.get("errorMsg"))) {
                smsSendResDTO.setCode("500");
                smsSendResDTO.setMsg("创蓝短信发送失败：" + resMap.get("errorMsg"));
//                tip = new ErrorTip(500, "创蓝短信发送失败：" + resMap.get("errorMsg"));
                smsSendResDTO.setStatus("FAIL");
                res = "创蓝短信发送失败：" + resMap.get("errorMsg");
                baseSmsLog.setStatus(4);
            }

            /*
            {
                 "code" : "0", //状态码
                 "msgId" : "17041010383624511", //消息Id
                 "errorMsg" : "", //失败状态码说明（成功返回空）
                 "time" : "20170410103836" //响应时间
             }
             */
            log.info("创蓝短信发送返回结果：{}", resMap);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("创蓝短信发送失败");
            smsSendResDTO.setCode("500");
            smsSendResDTO.setMsg("创蓝短信发送失败：");
            smsSendResDTO.setStatus("FAIL");
//            tip = new ErrorTip(500, "创蓝短信发送失败");
            res = e.getMessage();
            baseSmsLog.setStatus(4);
        }

        //更新短信

        baseSmsLog.setResTime(new Date());
        baseSmsLog.setSendRes(res);

        smsLogService.updateById(baseSmsLog);
        return smsSendResDTO;
    }

    /**
     * 创建短信内容体
     *
     * @param phone
     * @param msg
     * @param type
     * @return
     */
    private ClMsgContent createMsgContent(String phone, String msg, String type) {
        ClMsgContent clMsgContent = new ClMsgContent();
        if ("YX".equals(type)) {
            clMsgContent.setAccount(ydPropertites.getCl().getYxaccount());
            clMsgContent.setPassword(ydPropertites.getCl().getYxpassword());
        } else {
            clMsgContent.setAccount(ydPropertites.getCl().getAccount());
            clMsgContent.setPassword(ydPropertites.getCl().getPassword());
        }

        clMsgContent.setMsg(msg);
        clMsgContent.setPhone(phone);
//        clMsgContent.setSendtime();//不填写，默认马上发送
//        clMsgContent.setReport();//是否需要报告，默认false
//        clMsgContent.setExtend();//用户自定义扩展码，纯数字，建议1-3位（选填参数）
//        clMsgContent.setUid();//自助通系统内使用UID判断短信使用的场景类型，可重复使用，可自定义场景名称，示例如 VerificationCode（选填参数）
        return clMsgContent;
    }

    /**
     * 插入短信日志
     *
     * @param phone
     * @param msg
     * @return
     */
    public BaseSmsLog insertSmsLog(String phone, String msg) {
        BaseSmsLog smsLog = new BaseSmsLog();
        smsLog.setPhone(phone);//手机号
        smsLog.setSmsContent(msg);//短信内容
        smsLog.setReqTime(new Date());
        boolean b = smsLogService.insert(smsLog);
        return smsLog;
    }

}
