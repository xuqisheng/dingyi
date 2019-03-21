package com.zhidianfan.pig.log.dao.service.impl;

import com.zhidianfan.pig.log.dao.entity.LogTask;
import com.zhidianfan.pig.log.dao.mapper.LogTaskMapper;
import com.zhidianfan.pig.log.dao.service.ILogTaskService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务执行日志 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-10-26
 */
@Service
public class LogTaskServiceImpl extends ServiceImpl<LogTaskMapper, LogTask> implements ILogTaskService {

}
