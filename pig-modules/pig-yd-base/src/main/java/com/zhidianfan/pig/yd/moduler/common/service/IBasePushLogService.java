package com.zhidianfan.pig.yd.moduler.common.service;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BasePushLog;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 * 极光推送历史表 服务类
 * </p>
 *
 * @author sherry
 * @since 2018-08-21
 */
public interface IBasePushLogService extends IService<BasePushLog> {

    void toHis(Date date);

    void deleteHisData();
}
