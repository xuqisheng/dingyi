package com.zhidianfan.pig.yd.sms.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.sms.dao.entity.ConfigSms;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2018-11-02
 */
public interface IConfigSmsService extends IService<ConfigSms> {

    Double calChannelSucRate(Long operatorId);
}
