package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.LogVersion;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.LogVersionMapper;
import com.zhidianfan.pig.yd.moduler.common.service.ILogVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 叮叮/手机端账号登录版本记录表 log_version 服务实现类
 * </p>
 *
 * @author 李凌峰
 * @since 2019-03-12
 */
@Service
public class LogVersionServiceImpl extends ServiceImpl<LogVersionMapper, LogVersion> implements ILogVersionService {

}
