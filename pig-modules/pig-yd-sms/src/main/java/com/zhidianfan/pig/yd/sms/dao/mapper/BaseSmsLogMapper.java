package com.zhidianfan.pig.yd.sms.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhidianfan.pig.yd.sms.dao.entity.BaseSmsLog;

/**
 * <p>
 * 短信发送记录（与业务无关） Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-08-31
 */
public interface BaseSmsLogMapper extends BaseMapper<BaseSmsLog> {

    void updateCallBackSucStatus(BaseSmsLog baseSmsLog);

    void updateCallBackFailStatus(BaseSmsLog baseSmsLog);
}
