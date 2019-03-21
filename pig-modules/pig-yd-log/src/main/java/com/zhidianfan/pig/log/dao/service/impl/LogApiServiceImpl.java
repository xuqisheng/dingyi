package com.zhidianfan.pig.log.dao.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.log.dao.entity.LogApi;
import com.zhidianfan.pig.log.dao.mapper.LogApiMapper;
import com.zhidianfan.pig.log.dao.service.ILogApiService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础请求响应日志 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-09-02
 */
@Service
public class LogApiServiceImpl extends ServiceImpl<LogApiMapper, LogApi> implements ILogApiService {

}
