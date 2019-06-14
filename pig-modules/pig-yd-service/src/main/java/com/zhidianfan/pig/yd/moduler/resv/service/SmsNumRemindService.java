package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSms;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsNumRemind;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessSmsService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsNumRemindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: hzp
 * @Date: 2019-06-14 09:11
 * @Description:
 */
@Service
@Slf4j
public class SmsNumRemindService {


    /**
     * 酒店短信提醒记录接口
     */
    @Autowired
    private ISmsNumRemindService iSmsNumRemindService;

    /**
     * 酒店短信数量数据接口
     */
    @Autowired
    private IBusinessSmsService businessSmsService;


    @Autowired
    private IBusinessService iBusinessService;

    /**
     * @param businessId 酒店id
     * @param clientType  客户端id  1. 安卓电话机 2. 小程序
     * @return 是否提醒
     */
    public boolean getRemind(Integer businessId , Integer clientType) {


        // 0.查询该 酒店现在短信数量
        BusinessSms businessSms = businessSmsService.selectOne(new EntityWrapper<BusinessSms>()
                .eq("business_id", businessId));

        Integer currentSmsNum = businessSms.getCurrentSmsNum();

        //档次类型
        Integer remindType;

        //如果当前短信数量大于 50 就不需要提醒
        if (currentSmsNum >= 50){
            return false;
        }else if (currentSmsNum >= 20){
            // 50 到20 第一档提醒
            remindType  = 1;
        } else {
            //  小于 20 的提醒
            remindType  = 2;
        }



        // 1.查询该酒店短信提醒设置
        SmsNumRemind smsNumRemind = iSmsNumRemindService.selectOne(new EntityWrapper<SmsNumRemind>()
                .eq("business_id", businessId)
                .eq("client_type", clientType)
                .eq("client_type", remindType));


        // 1.1 若为空,则初始化
        if (smsNumRemind == null){

            //插入该档位的数据
            initBusinessSmsNumRemind(businessId,clientType,remindType);
            smsNumRemind = iSmsNumRemindService.selectOne(new EntityWrapper<SmsNumRemind>()
                    .eq("business_id", businessId)
                    .eq("client_type", clientType)
                    .eq("remind_type", remindType));
        }



        // 提醒次数设置为0
        if (smsNumRemind.getRemindNum() != 0){

            smsNumRemind.setRemindNum(0);
            iSmsNumRemindService.updateById(smsNumRemind);

            return true;
        }else {
            return false;
        }

        // 充值的时候,还要去重置这张表里的对应的酒店数据 提醒次数设置为1
    }

    /**
     * 初始化该程序端的数据
     * @param businessId 酒店id
     * @param clientType 客户端id  1 .安卓电话机   2.小程序
     * @param remindType 提醒档位 1.  50 条  2 .  20条
     */
    private void initBusinessSmsNumRemind(Integer businessId,Integer clientType, Integer remindType) {


        Business business = iBusinessService.selectById(businessId);


        SmsNumRemind smsNumRemind = new SmsNumRemind();
        smsNumRemind.setBusinessId(businessId);
        smsNumRemind.setBusinesssName(business.getBusinessName());
        smsNumRemind.setClientType(clientType);
        smsNumRemind.setRemindType(remindType);
        smsNumRemind.setRemindNum(1);
        smsNumRemind.setCreateAt(new Date());
        iSmsNumRemindService.insert(smsNumRemind);
    }
}
