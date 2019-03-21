package com.zhidianfan.pig.log.dao.service.impl;

import com.zhidianfan.pig.log.dao.entity.LogKeyOper;
import com.zhidianfan.pig.log.dao.mapper.LogKeyOperMapper;
import com.zhidianfan.pig.log.dao.service.ILogKeyOperService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关键操作日志记录 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-10-31
 */
@Service
public class LogKeyOperServiceImpl extends ServiceImpl<LogKeyOperMapper, LogKeyOper> implements ILogKeyOperService {

}
