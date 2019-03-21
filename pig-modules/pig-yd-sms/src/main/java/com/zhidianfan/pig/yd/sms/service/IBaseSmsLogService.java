package com.zhidianfan.pig.yd.sms.service;


import com.baomidou.mybatisplus.service.IService;
import com.zhidianfan.pig.yd.sms.dao.entity.BaseSmsLog;

/**
 * <p>
 * 短信发送记录（与业务无关） 服务类
 * </p>
 *
 * @author sherry
 * @since 2018-08-31
 */
public interface IBaseSmsLogService extends IService<BaseSmsLog> {

    void updateCallBackSucStatus(BaseSmsLog baseSmsLog);

    void updateCallBackFailStatus(BaseSmsLog baseSmsLog);
}
