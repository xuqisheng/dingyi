package com.zhidianfan.pig.yd.moduler.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSmsRechargeLog;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.resv.bo.SmsRecordBO;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
public interface IBusinessSmsRechargeLogService extends IService<BusinessSmsRechargeLog> {

    Map<String, String> getGeneralRechargeRecord(Integer businessId);

    Page<SmsRecordBO> selectSmsRechargeRecordPage(Page<SmsRecordBO> page, Integer businessId);

    /**
     * 记录充值记录
     * @param date
     */
    void insertRechargeLog(Map<String, String> date);
}
