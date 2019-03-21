package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigVersion;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.ConfigVersionMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IConfigVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户端版本配置表 config_version 服务实现类
 * </p>
 *
 * @author 李凌峰
 * @since 2019-03-12
 */
@Service
public class ConfigVersionServiceImpl extends ServiceImpl<ConfigVersionMapper, ConfigVersion> implements IConfigVersionService {

}
