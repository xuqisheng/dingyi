package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.BaseSmsLog;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.BaseSmsLogMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IBaseSmsLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

}
