package com.zhidianfan.pig.yd.sms.service.impl;

import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.sms.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.sms.dao.entity.BaseSmsLog;
import com.zhidianfan.pig.yd.sms.dto.ClMsgContent;
import com.zhidianfan.pig.yd.sms.dto.SmsSendResDTO;
import com.zhidianfan.pig.yd.sms.service.ChuangLSmsService;
import com.zhidianfan.pig.yd.sms.service.IBaseSmsLogService;
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
 * @Author: huzp
 * @Date: 2018/11/2 15:11
 * @DESCRIPTION 创蓝短信发送
 */
@Service
public class ChuangLSmsServiceImpl implements ChuangLSmsService {


    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private YdPropertites ydPropertites;


    @Autowired
    private IBaseSmsLogService baseSmsLogService;


    /**
     * 创蓝短信发送短信发送
     *
     * @param phone
     * @param msg
     * @return
     */
    @Override
    public SmsSendResDTO sendMsg(String phone, String msg, String type, long smsId) {


        ClMsgContent clMsgContent = createMsgContent(phone, msg, type,"");
        log.info("构造创蓝短信体:" +clMsgContent.toString());


        SmsSendResDTO smsSendResDTO = getSmsSendResDTO(clMsgContent, smsId);
        return smsSendResDTO;
    }


    @Override
    public SmsSendResDTO sendMsgV2(String phone, String msg, String type, long smsId, String sendtime) {


        //构造创蓝短信体
        ClMsgContent clMsgContent = createMsgContent(phone, msg, type,sendtime);
        log.info("构造创蓝短信体:" +clMsgContent.toString());


        return getSmsSendResDTO(clMsgContent, smsId);
    }


    private SmsSendResDTO getSmsSendResDTO(ClMsgContent clMsgContent, long smsId) {

        //如果环境是测试环境或者本地环境则直接打印日志 不实际发送短信
        //测试环境下不发送短信
        if ("dev".equals(ydPropertites.getActive())
                || "test".equals(ydPropertites.getActive())) {

            String msgid = String.valueOf(System.currentTimeMillis());

            SmsSendResDTO smsSendResDTO = new SmsSendResDTO();
            BaseSmsLog baseSmsLog = new BaseSmsLog();

            log.debug("ydPropertites.getFlag()" + ydPropertites.getFlag());
            if("success".equals(ydPropertites.getFlag())){
                //时间戳作为测试短信id
                smsSendResDTO.setCode("0");
                smsSendResDTO.setMsgId(Long.parseLong(msgid));
                smsSendResDTO.setStatus("SUCCESS");
                smsSendResDTO.setErrorMsg("errorMsg");
                smsSendResDTO.setTime(new Date().toString());
                smsSendResDTO.setText(clMsgContent.getMsg());

                baseSmsLog.setId(smsId);
                baseSmsLog.setOperator(1);
                //更新msgid 本地和
                baseSmsLog.setStatus(1);
                baseSmsLog.setMsgid(msgid);
                //设置提交成功
                //更新短信
                baseSmsLog.setSendRes("这是条测试短信发送成功");
                baseSmsLog.setResTime(new Date());

                baseSmsLogService.updateById(baseSmsLog);
                log.debug("发送短信成功");

                //更新结果表发送的短信
                baseSmsLogService.updateCallBackSucStatus(baseSmsLog);
            }else {
                smsSendResDTO.setCode("500");
                smsSendResDTO.setMsgId(null);
                smsSendResDTO.setStatus("FAIL");
                smsSendResDTO.setErrorMsg("手机号码个数错误");
                smsSendResDTO.setTime(new Date().toString());
                smsSendResDTO.setText(null);

                baseSmsLog.setId(smsId);
                baseSmsLog.setOperator(1);
                //更新msgid 本地和
                baseSmsLog.setStatus(3);
                baseSmsLog.setMsgid(msgid);
                //设置提交成功
                //更新短信
                baseSmsLog.setSendRes("这是条测试短信发送失败");
                baseSmsLog.setResTime(new Date());

                baseSmsLogService.updateById(baseSmsLog);
                log.debug("测试环境发送短信失败");

                //更新结果表发送的短信
                baseSmsLogService.updateCallBackSucStatus(baseSmsLog);
            }


            return smsSendResDTO;
        }

        //构造json
        String jsonStr = JsonUtils.obj2Json(clMsgContent);

        String url = ydPropertites.getCl().getUrl();

        BaseSmsLog baseSmsLog = new BaseSmsLog();
        baseSmsLog.setId(smsId);
        baseSmsLog.setOperator(1);

//        Tip tip = SuccessTip.SUCCESS_TIP;
        SmsSendResDTO smsSendResDTO = new SmsSendResDTO();
        smsSendResDTO.setOperator(1);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");

        HttpEntity httpEntity = new HttpEntity(jsonStr, httpHeaders);
        ResponseEntity<Map> responseEntity;
        String res;
        try {

            responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            Map<String, Object> resMap = responseEntity.getBody();

            res = JsonUtils.obj2Json(resMap);
            smsSendResDTO.setCode(MapUtils.getString(resMap, "code"));


            Long msgId = MapUtils.getLong(resMap, "msgId");
            if (msgId == null) {
                //更新msgid
                baseSmsLog.setMsgid("");
            } else {
                //更新msgid
                baseSmsLog.setMsgid(msgId.toString());
            }

            //设置提交成功
            baseSmsLog.setStatus(1);

            smsSendResDTO.setMsgId(msgId);
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

                //计算短信失败条数
                int smsNum = clMsgContent.getMsg().length() <= 70 ? 1 : (2 + (clMsgContent.getMsg().length() - 70) / 67);
                smsNum = smsNum * clMsgContent.getPhone().split(",").length;
                baseSmsLog.setFailnum(smsNum);
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
            smsSendResDTO.setErrorMsg("yd-sms 发送短信异常");
//            tip = new ErrorTip(500, "创蓝短信发送失败");
            res = e.getMessage();
            baseSmsLog.setStatus(4);

            //计算短信失败条数
            int smsNum = clMsgContent.getMsg().length() <= 70 ? 1 : (2 + (clMsgContent.getMsg().length() - 70) / 67);
            smsNum = smsNum * clMsgContent.getPhone().split(",").length;
            baseSmsLog.setFailnum(smsNum);

        }

        //更新短信
        baseSmsLog.setResTime(new Date());
        baseSmsLog.setSendRes(res);

        baseSmsLogService.updateById(baseSmsLog);
        return smsSendResDTO;
    }



    /**
     * 创建短信内容体
     *
     * @param phone
     * @param msg
     * @param type
     * @param sendTime
     * @return
     */
    private ClMsgContent createMsgContent(String phone, String msg, String type,String sendTime) {
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
        clMsgContent.setSendtime(sendTime);//不填写，默认马上发送
        clMsgContent.setReport("true");//是否需要报告，默认false 为不使用回调

        return clMsgContent;
    }
}
