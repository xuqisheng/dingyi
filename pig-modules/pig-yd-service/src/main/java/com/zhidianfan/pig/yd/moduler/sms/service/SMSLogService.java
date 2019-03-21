package com.zhidianfan.pig.yd.moduler.sms.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsLog;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsReply;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsLogService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsReplyService;
import com.zhidianfan.pig.yd.moduler.dbo.log.SMSLogDO;
import com.zhidianfan.pig.yd.moduler.dbo.log.SMSReplyDO;
import com.zhidianfan.pig.yd.moduler.sms.bo.log.SMSLogBO;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSLogDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSReplyDTO;
import com.zhidianfan.pig.yd.moduler.sms.util.BeanCopierUtil;
import com.zhidianfan.pig.yd.moduler.sms.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信接口业务层
 * Created by Administrator on 2017/12/14.
 */
@Service
public class SMSLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSLogService.class);

//    @Resource
//    private SMSLogMapper smsLogMapper;

    @Autowired
    private ISmsLogService smsLogService;

    @Autowired
    private ISmsReplyService smsReplyService;

    @Transactional
    public void insertSMSLog(SMSLogDTO smsLogDTO) {
        if (smsLogDTO != null){
            if (smsLogDTO.getBusinessId() == null || smsLogDTO.getBusinessId() == 0){
                LOGGER.error("插入短信日志 接收参数不全, 酒店id 不能为空!");
            }
            if (smsLogDTO.getVipPhone() == null || "".equals(smsLogDTO.getVipPhone())){
                LOGGER.error("插入短信日志 接收参数不全, 客户手机号 不能为空!");
            }
            if (smsLogDTO.getContent() == null){
                smsLogDTO.setContent("");
            }
        }
        SMSLogBO smsLogBO = new SMSLogBO();
        List<SMSLogDTO> smsLogDTOList = new ArrayList<>();
        try {
            BeanCopierUtil.copyProperties(smsLogDTO, smsLogBO);
            String phoneStr = smsLogBO.getVipPhone();
            List<String> phoneList = StringUtil.string2List(phoneStr);
            for (String phone: phoneList){
                smsLogDTO = new SMSLogDTO();
                if (!StringUtil.checkCellphone(phone)){
                    LOGGER.error("插入短信日志 接收参数错误, 客户手机号 {} 校验错误!", phone);
//                    smsLogDO.setStatusDesc("插入短信日志 接收参数错误, 客户手机号 "+phone+" 校验错误!");
//                    smsLogDO.setStatus("error_phone");
//                    continue;
                }
                BeanCopierUtil.copyProperties(smsLogBO, smsLogDTO);
                smsLogDTO.setVipPhone(phone);
                smsLogDTOList.add(smsLogDTO);
            }

            if (smsLogDTOList.size() > 0) {
                Integer a = smsLogService.insertSmsLog(smsLogDTOList);
                if (a != null && a > 0){
                    LOGGER.info("SUCCESS");
                } else {
                    LOGGER.error("插入短信日志 内部错误, 错误信息为: 插入条数为0");
                }
            } else {
                LOGGER.error("插入短信日志 接收参数错误, 客户手机号全部校验错误!");
            }
        } catch (Exception e){
            LOGGER.error("插入短信日志 内部错误, 错误信息为: {}", e.getMessage());
        }
    }

    @Transactional
    public void updateSMSLog(SMSLogDTO smsLogDTO) {
        if (smsLogDTO != null){
            if (smsLogDTO.getMsgid() == null || "".equals(smsLogDTO.getMsgid())){
                LOGGER.error("更新短信日志 接收参数不全, msgid 不能为空!");
            }
            if (smsLogDTO.getStatus() == null || "".equals(smsLogDTO.getStatus())){
                LOGGER.error("更新短信日志 接收参数不全, 短信发送状态 不能为空!");
            }
            if (smsLogDTO.getStatusDesc() == null || "".equals(smsLogDTO.getStatusDesc())){
                LOGGER.error("更新短信日志 接收参数不全, 短信发送状态描述 不能为空!");
            }
        }
        SmsLog smsLog = new SmsLog();
        try {
            if (smsLogDTO != null){
                BeanCopierUtil.copyProperties(smsLogDTO, smsLog);
            }
            boolean a = smsLogService.update(smsLog,new EntityWrapper<SmsLog>().eq("msgid",smsLogDTO.getMsgid()));
            if (a == true){
                LOGGER.info("SUCCESS");
            } else {
                LOGGER.error("更新短信日志 内部错误, 错误信息为: 更新条数为0");
            }
        } catch (Exception e){
            LOGGER.error("更新短信日志 内部错误, 错误信息为: {}", e.getMessage());
        }
    }

    @Transactional
    public void insertSMSReply(SMSReplyDTO smsReplyDTO) {
        if (smsReplyDTO != null){
            if (smsReplyDTO.getMsgid() == null || "".equals(smsReplyDTO.getMsgid())){
                LOGGER.error("更新短信日志 接收参数不全, msgid 不能为空!");
            }
            if (smsReplyDTO.getReplyContent() == null){
                smsReplyDTO.setReplyContent("");
            }
        }
        SmsReply smsReply = new SmsReply();
        try {
            if (smsReplyDTO != null){
                BeanCopierUtil.copyProperties(smsReplyDTO, smsReply);
            }
            boolean a = smsReplyService.insert(smsReply);
            if (a == true){
                LOGGER.info("SUCCESS");
            } else {
                LOGGER.error("更新短信日志 内部错误, 错误信息为: 更新条数为0");
            }
        } catch (Exception e){
            LOGGER.error("更新短信日志 内部错误, 错误信息为: {}", e.getMessage());
        }

    }
}
