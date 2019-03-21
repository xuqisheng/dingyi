package com.zhidianfan.pig.yd.sms.dao.mapper;

import com.zhidianfan.pig.yd.sms.dao.entity.ConfigSms;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2018-11-02
 */
public interface ConfigSmsMapper extends BaseMapper<ConfigSms> {

    Double calChannelSucRate(@Param("operatorId") Long operatorId);
}
