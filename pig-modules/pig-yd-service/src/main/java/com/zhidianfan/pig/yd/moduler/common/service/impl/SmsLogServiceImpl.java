package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsLog;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.SmsLogMapper;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.sms.dto.log.SMSLogDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Conan
 * @since 2018-09-06
 */
@Service
public class SmsLogServiceImpl extends ServiceImpl<SmsLogMapper, SmsLog> implements ISmsLogService {

    @Override
    public Integer insertSmsLog(List<SMSLogDTO> smsLogDTOList){ return this.baseMapper.insertSmsLog(smsLogDTOList); }

    @Override
    public Integer sendSmsNum(Date startDate, Date endDate,Integer businessId) {
        return this.baseMapper.sendSmsNum(startDate,endDate,businessId);
    }

}
