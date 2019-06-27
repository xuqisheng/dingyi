package com.zhidianfan.pig.yd.moduler.resv.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSms;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSmsRechargeLog;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsInvoiceRecord;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.manage.feign.AuthFeign;
import com.zhidianfan.pig.yd.moduler.resv.bo.BusinessConsumeBo;

import com.zhidianfan.pig.yd.moduler.resv.dto.SmsInvoiceRecordDTO;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.SmsSendResDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;

import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-08
 * @Time: 15:36
 */
@Service
@Slf4j
public class BusinessSmsService {

    @Resource
    private ISmsLogService iSmsLogService;

    @Resource
    private ISmsMarketingService iSmsMarketingService;


    @Resource
    private IBusinessSmsService iBusinessSmsService;


    @Resource
    private ISmsInvoiceRecordService iSmsInvoiceRecordService;

    @Resource
    private IBusinessSmsRechargeLogService iBusinessSmsRechargeLogService;

    @Resource
    private SmsFeign smsFeign;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AuthFeign authFeign;

    @Resource
    private IBusinessService ibusinessService;

    /**
     * 消耗短信统计
     * @param startDate
     * @param endDate
     * @return
     */
    public List<BusinessConsumeBo> consumeSms(LocalDate startDate, LocalDate endDate,Byte type,Integer businessId){

        List<BusinessConsumeBo> list = new ArrayList<>();

        ZoneId zone = ZoneId.systemDefault();
        int months = Period.between(startDate, endDate).getMonths();
        if(months==0){
            BusinessConsumeBo businessConsumeBo = new BusinessConsumeBo();
            Date start = Date.from(startDate.atStartOfDay().atZone(zone).toInstant());
            Date end = Date.from(endDate.atStartOfDay().atZone(zone).toInstant());

            Integer smsNum=0;
            if(type==1){
                 smsNum = iSmsLogService.sendSmsNum(start, end,businessId);
            }else if(type==2){
                smsNum = iSmsMarketingService.sendSmsNum(start, end,businessId);
            }
            businessConsumeBo.setEndDate(end);
            businessConsumeBo.setStartDate(start);
            businessConsumeBo.setNum(smsNum);
            list.add(businessConsumeBo);
        }else {
            for (int i = 0; i < months; i++) {
                BusinessConsumeBo businessConsumeBo = new BusinessConsumeBo();

                Date start = Date.from(startDate.plusMonths(i).atStartOfDay().atZone(zone).toInstant());
                Date end = Date.from(startDate.plusMonths(i+1).atStartOfDay().atZone(zone).toInstant());
                if(i==months-1){
                    end = Date.from(endDate.atStartOfDay().atZone(zone).toInstant());
                }

                Integer smsNum=0;
                if(type==1){
                    smsNum = iSmsLogService.sendSmsNum(start, end,businessId);
                }else if(type==2){
                    smsNum = iSmsMarketingService.sendSmsNum(start, end,businessId);
                }

                businessConsumeBo.setStartDate(start);
                businessConsumeBo.setEndDate(end);
                businessConsumeBo.setNum(smsNum);
                list.add(businessConsumeBo);
            }
        }

        return list;
    }


    /**
     *
     * @param businessId
     * @return
     */
    public Integer leftSms(Integer businessId){

        BusinessSms condition = new BusinessSms();
        condition.setBusinessId(businessId);
        BusinessSms one =iBusinessSmsService.selectOne(new EntityWrapper<>(condition));
        if(one==null){
            return 0;
        }
        return one.getCurrentSmsNum();
    };


    /**
     * 开票申请
     * @param smsInvoiceRecordDTO
     * @return
     */
    @Transactional
    public Boolean invoice( SmsInvoiceRecordDTO smsInvoiceRecordDTO){

        SmsInvoiceRecord smsInvoiceRecord = new SmsInvoiceRecord();
        //判断是否已开票
        smsInvoiceRecord.setRechargeLogId(smsInvoiceRecordDTO.getRechargeLogId());
        smsInvoiceRecord.setStatus(1);
        int i = iSmsInvoiceRecordService.selectCount(new EntityWrapper<>(smsInvoiceRecord));
        if(i>0){
            throw new RuntimeException("此充值已有开票记录");
        }

        //添加开票申请
        BeanUtils.copyProperties(smsInvoiceRecordDTO,smsInvoiceRecord);
        smsInvoiceRecord.setCreatedTime(new Date());
        boolean insert = iSmsInvoiceRecordService.insert(smsInvoiceRecord);
        //修改充值记录
        BusinessSmsRechargeLog updateData = new BusinessSmsRechargeLog();
        updateData.setId(smsInvoiceRecordDTO.getRechargeLogId());
        updateData.setInvoiceStatus(1);
        boolean b = iBusinessSmsRechargeLogService.updateById(updateData);
        if(insert&&b){
            return true;
        }else {
            throw new RuntimeException("开票申请失败");
         }
    }


    public boolean sendResetPasswordCode(String mobile){

        String code = RandomStringUtils.random(4, "0123456789");
        String content = "重置密码操作，验证码："+code+"。如非本人操作，请忽略本短信";

        SmsSendResDTO smsSendResDTO = smsFeign.sendNormalMsg(mobile, content);
        if("0".equals(smsSendResDTO.getCode())){

            redisTemplate.opsForValue().set("RESET_PASSWORD_CODE_"+mobile,code,60*10, TimeUnit.SECONDS);

            log.info("重置密码短信验证码："+code) ;
            return true;
        }else {
            log.info("重置密码短信验证码发送失败："+smsSendResDTO.getMsg()+smsSendResDTO.getErrorMsg());
            return false;
        }
    };


    public boolean verifyResetPasswordCode(String mobile,String code){
        Object saveCode = redisTemplate.opsForValue().get("RESET_PASSWORD_CODE_" + mobile);
        if(Objects.equals(saveCode,code) &&code!=null){
            return true;
        }
        return false;
    };

    public boolean resetPassword(String mobile, String password, String code) {
        boolean b = verifyResetPasswordCode(mobile, code);
        if(b) {
            SuccessTip tip = authFeign.updateUserPassword(mobile, password);
            if(200==tip.getCode()){
                redisTemplate.delete("RESET_PASSWORD_CODE_" + mobile);
                log.info("验证码已被验证："+code+"-"+mobile);

                Business business = new Business();
                business.setLoginPassword(password);
                Business condition = new Business();
                condition.setLoginUser(mobile);
                ibusinessService.update(business, new EntityWrapper<>(condition));
                return true;
            }
        }
        return false;
    }


}
