package com.zhidianfan.pig.log.dao.service.impl;

import com.zhidianfan.pig.log.dao.entity.LogDevLogin;
import com.zhidianfan.pig.log.dao.mapper.LogDevLoginMapper;
import com.zhidianfan.pig.log.dao.service.ILogDevLoginService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备登陆登出日志 服务实现类
 * </p>
 *
 * @author sherry
 * @since 2018-11-15
 */
@Service
public class LogDevLoginServiceImpl extends ServiceImpl<LogDevLoginMapper, LogDevLogin> implements ILogDevLoginService {

}
