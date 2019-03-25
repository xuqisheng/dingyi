package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsValidate;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2019-03-25
 * @Modified By:
 */
@Service
public class SmsService {
    @Autowired
    private ISmsValidateService smsValidateService;

    public boolean checkMobileSendTime(String mobile) {
        SmsValidate smsValidate = smsValidateService.selectOne(new EntityWrapper<SmsValidate>().eq("mobile", mobile).orderBy("create_time", false));
        if(null == smsValidate){
            return true;
        }
        Instant instant = smsValidate.getCreateTime().toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime createTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime now = LocalDateTime.now().minusMinutes(1);
        boolean before = createTime.isBefore(now);
        return before;
    }
}
