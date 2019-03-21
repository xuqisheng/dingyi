package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSmsRechargeLog;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BusinessSmsRechargeLogMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessSmsRechargeLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.resv.bo.SmsRecordBO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
@Service
public class BusinessSmsRechargeLogServiceImpl extends ServiceImpl<BusinessSmsRechargeLogMapper, BusinessSmsRechargeLog> implements IBusinessSmsRechargeLogService {

    @Override
    public Map<String, String> getGeneralRechargeRecord(Integer businessId) {

        return baseMapper.getGeneralRechargeRecord(businessId);
    }

    @Override
    public Page<SmsRecordBO> selectSmsRechargeRecordPage(Page<SmsRecordBO> page, Integer businessId) {

        List<SmsRecordBO> smsRecordBOS = baseMapper.selectSmsRechargeRecordPage(page, businessId);

        return page.setRecords(smsRecordBOS);
    }

    @Override
    public void insertRechargeLog(Map<String, String> date) {
        baseMapper.insertRechargeLog(date);
    }
}
