package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BasePushLog;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BasePushLogMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBasePushLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 极光推送历史表 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-08-21
 */
@Service
public class BasePushLogServiceImpl extends ServiceImpl<BasePushLogMapper, BasePushLog> implements IBasePushLogService {

    @Override
    public void toHis(Date date) {
        this.baseMapper.toHis(date);
    }

    @Override
    public void deleteHisData() {
        this.baseMapper.deleteHisData();
    }
}
