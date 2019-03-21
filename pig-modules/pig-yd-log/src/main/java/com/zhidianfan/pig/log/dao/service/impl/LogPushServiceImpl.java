package com.zhidianfan.pig.log.dao.service.impl;

import com.zhidianfan.pig.log.dao.entity.LogPush;
import com.zhidianfan.pig.log.dao.mapper.LogPushMapper;
import com.zhidianfan.pig.log.dao.service.ILogPushService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 推送日志 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-11-05
 */
@Service
public class LogPushServiceImpl extends ServiceImpl<LogPushMapper, LogPush> implements ILogPushService {

}
