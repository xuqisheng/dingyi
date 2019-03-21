package com.zhidianfan.pig.yd.sms.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.sms.dao.entity.BaseSmsLog;
import com.zhidianfan.pig.yd.sms.dao.mapper.BaseSmsLogMapper;
import com.zhidianfan.pig.yd.sms.service.IBaseSmsLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信发送记录（与业务无关） 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-08-31
 */
@Service
public class BaseSmsLogServiceImpl extends ServiceImpl<BaseSmsLogMapper, BaseSmsLog> implements IBaseSmsLogService {

    @Override
    public void updateCallBackSucStatus(BaseSmsLog baseSmsLog) {
        baseMapper.updateCallBackSucStatus(baseSmsLog);
    }

    @Override
    public void updateCallBackFailStatus(BaseSmsLog baseSmsLog) {
        baseMapper.updateCallBackFailStatus(baseSmsLog);
    }
}
