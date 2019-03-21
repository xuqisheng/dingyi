package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsMarketing;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.SmsMarketingMapper;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsMarketingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
@Service
public class SmsMarketingServiceImpl extends ServiceImpl<SmsMarketingMapper, SmsMarketing> implements ISmsMarketingService {


    @Override
    public Integer sendSmsNum(Date startDate, Date endDate,Integer businessId) {
        return this.baseMapper.sendSmsNum(startDate,endDate,businessId);
    }


}
