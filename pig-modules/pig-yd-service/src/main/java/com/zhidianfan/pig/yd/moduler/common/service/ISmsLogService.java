package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsLog;
import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSLogDTO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Conan
 * @since 2018-09-06
 */
public interface ISmsLogService extends IService<SmsLog> {

    Integer insertSmsLog(List<SMSLogDTO> smsLogDTOList);


    Integer sendSmsNum(Date startDate,Date endDate,Integer businessId);

}
