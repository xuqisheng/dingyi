package com.zhidianfan.pig.yd.sms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.sms.dao.entity.ConfigSms;
import com.zhidianfan.pig.yd.sms.dao.mapper.ConfigSmsMapper;
import com.zhidianfan.pig.yd.sms.service.IConfigSmsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2018-11-02
 */
@Service
public class ConfigSmsServiceImpl extends ServiceImpl<ConfigSmsMapper, ConfigSms> implements IConfigSmsService {


    /**
     * 计算通道短信发送成功率
     * @param operatorId 运营商id
     * @return 返回成功率
     */
    @Override
    public Double calChannelSucRate(Long operatorId) {

        Double sucRate = baseMapper.calChannelSucRate(operatorId);

        return sucRate;
    }
}
