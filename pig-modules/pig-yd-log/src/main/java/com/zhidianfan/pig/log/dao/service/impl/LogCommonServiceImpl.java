package com.zhidianfan.pig.log.dao.service.impl;

import com.zhidianfan.pig.log.dao.entity.LogCommon;
import com.zhidianfan.pig.log.dao.mapper.LogCommonMapper;
import com.zhidianfan.pig.log.dao.service.ILogCommonService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用日志表 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
@Service
public class LogCommonServiceImpl extends ServiceImpl<LogCommonMapper, LogCommon> implements ILogCommonService {

}
