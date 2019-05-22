package com.zhidianfan.pig.yd.moduler.sms.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSms;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessSmsService;
import com.zhidianfan.pig.yd.moduler.sms.bo.sms.SmsResultBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.SMSDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSLogDTO;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.ClMsgParam;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.SmsSendResDTO;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmsService {

    /**
     * 微服务之间调用pig-yd-base中短信接口
     */
    @Autowired
    private SmsFeign smsFeign;

    @Autowired
    private SMSLogService sMSLogService;

    @Autowired
    private IBusinessSmsService businessSmsService;

    //短信头，如果没有短信头就默认易订
    private static final String SIGNER = "【易订】";

    //短信结尾
    private static final String ENDING = "系统短信，请勿回复！";

    private static final Logger LOGGER = Logger.getLogger(SmsService.class);


    /**
     * 发送自定义短信
     *
     * @param smsDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public SmsResultBO sendSMS(SMSDTO smsDTO) {
            SmsResultBO resultBO = new SmsResultBO();
            smsDTO.setMessageContent(SIGNER + smsDTO.getMessageContent());
//            酒店短信查询
            BusinessSms businessSms = businessSmsService.selectOne(new EntityWrapper<BusinessSms>()
                    .eq("business_id",smsDTO.getBusinessId()));
            if(businessSms == null){
                LOGGER.error("酒店不存在或酒店未开通短信业务，请求酒店id为：" + smsDTO.getBusinessId());
                return getResult("0", "该酒店不存在或该酒店未开通短信业务！","FAIL");
            }
            Integer count = businessSms.getCurrentSmsNum();
            int smsNum = smsDTO.getMessageContent().length() <= 70 ? 1 : (2 + (smsDTO.getMessageContent().length() - 70) / 67);
            smsNum = smsNum * smsDTO.getPhone().split(",").length;
            LOGGER.info("message for" + smsDTO.getPhone());
            LOGGER.info("business id:" + smsDTO.getBusinessId() + "\nsms count:" + count);
            Boolean isRemind = null;
            if(count <= 0){
                isRemind = Boolean.FALSE;
                resultBO.setRemind(isRemind);
                return resultBO;
            }
            Integer minSmsNum = businessSms.getMinSmsNum();
            if (minSmsNum != null && minSmsNum > 0){
                if (count - smsNum < minSmsNum){
                    isRemind = Boolean.TRUE;
                } else {
                    isRemind = Boolean.FALSE;
                }
            }
        List<String> phonesList = Lists.newArrayList(StringUtils.split(smsDTO.getPhone(), ","));
        ClMsgParam clMsgParam = new ClMsgParam();
            if (count >= smsNum) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("msg", smsDTO.getMessageContent());
//                jsonObject.put("type", 2);
//                jsonObject.put("phone", smsDTO.getPhone());
//                Map<String, Object> result = ChuangLanUtils.sendMessage(jsonObject);
                SmsSendResDTO smsSendResDTO;
                if(phonesList.size()>1){
                    clMsgParam.setPhone(phonesList);
                    smsSendResDTO = smsFeign.sendBatchNormalMsg(clMsgParam);
                }
                else{
                    smsSendResDTO = smsFeign.sendNormalMsg(smsDTO.getPhone(),smsDTO.getMessageContent());
                }
                Map<String, Object> result = object2Map(smsSendResDTO);

                //短信发送日志
                SMSLogDTO log = new SMSLogDTO();
                log.setBusinessId(smsDTO.getBusinessId());
                log.setContent(smsDTO.getMessageContent());
                log.setVipPhone(smsDTO.getPhone());
                log.setSmsNum(smsNum);
                log.setMsgid(String.valueOf(resultBO.getMsgId()));

                if (result.get("code").equals("0")) {
                    BusinessSms businessSMS = new BusinessSms();
                    businessSMS.setBusinessId(smsDTO.getBusinessId().intValue());
                    businessSMS.setCurrentSmsNum(count - smsNum);
//                    发送完后修改短信数量
                    businessSmsService.update(businessSMS,new EntityWrapper<BusinessSms>()
                            .eq("business_id",smsDTO.getBusinessId()));
                    resultBO.setCode(MapUtils.getString(result, "code"));
                    resultBO.setMsgId(MapUtils.getLong(result, "msgId"));
                    resultBO.setStatus(MapUtils.getString(result, "status"));
                    resultBO.setTime(MapUtils.getString(result, "time"));
                    resultBO.setErrorMsg(MapUtils.getString(result, "errorMsg"));
                    resultBO.setRemind(isRemind);
                    resultBO.setMinSmsNum(minSmsNum);

                    log.setStatus("REQUEST_SUCCESS");
                    log.setStatusDesc("短信已发送");
                } else {
                    log.setStatus("REQUEST_FAIL");
                    log.setStatusDesc(MapUtils.getString(result, "errorMsg"));
                    resultBO.setCode(MapUtils.getString(result, "code"));
                    resultBO.setErrorMsg("发送信息失败!" + JSONObject.toJSONString(result));
                }
                sMSLogService.insertSMSLog(log);
                return resultBO;
            } else {
                resultBO.setCode("-200");
                resultBO.setErrorMsg("短信条数不足");
                resultBO.setStatus("FAIL");
                resultBO.setRemind(isRemind);
                resultBO.setMinSmsNum(minSmsNum);
                return resultBO;
            }

    }

    /**
     * 对象转map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 返回错误信息
     * @param code
     * @param text
     * @param status
     * @return
     */
    private SmsResultBO getResult(String code, String text, String status) {
        SmsResultBO resultBO = new SmsResultBO();
        resultBO.setErrorMsg(text);
        resultBO.setCode(code);
        resultBO.setStatus(status);
        return resultBO;
    }
}
