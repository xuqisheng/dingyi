package com.zhidianfan.pig.yd.moduler.common.dao.mapper;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BasePushLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Date;

/**
 * <p>
 * 极光推送历史表 Mapper 接口
 * </p>
 *
 * @author sherry
 * @since 2018-08-21
 */
public interface BasePushLogMapper extends BaseMapper<BasePushLog> {

    void toHis(Date date);

    void deleteHisData();
}
