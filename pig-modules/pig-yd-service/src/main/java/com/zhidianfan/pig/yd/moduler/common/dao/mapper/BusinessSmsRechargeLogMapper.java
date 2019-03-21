package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSmsRechargeLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.resv.bo.SmsRecordBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
public interface BusinessSmsRechargeLogMapper extends BaseMapper<BusinessSmsRechargeLog> {

    Map<String, String> getGeneralRechargeRecord(@Param("businessId") Integer businessId);

    List<SmsRecordBO> selectSmsRechargeRecordPage(Page<SmsRecordBO> page, @Param("businessId") Integer businessId);


    void insertRechargeLog(Map<String, String> date);
}
