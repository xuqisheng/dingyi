package com.zhidianfan.pig.log.dao.service.impl;

import com.zhidianfan.pig.log.dao.entity.LogSms;
import com.zhidianfan.pig.log.dao.mapper.LogSmsMapper;
import com.zhidianfan.pig.log.dao.service.ILogSmsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信发送记录 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
@Service
public class LogSmsServiceImpl extends ServiceImpl<LogSmsMapper, LogSms> implements ILogSmsService {

}
