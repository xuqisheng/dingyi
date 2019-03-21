package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSLogDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Conan
 * @since 2018-09-06
 */
public interface SmsLogMapper extends BaseMapper<SmsLog> {

    Integer insertSmsLog(List<SMSLogDTO> smsLogDTOList);

    Integer sendSmsNum(@Param("startDate") Date startDate, @Param("endDate")Date endDate,@Param("businessId")Integer businessId);
}
