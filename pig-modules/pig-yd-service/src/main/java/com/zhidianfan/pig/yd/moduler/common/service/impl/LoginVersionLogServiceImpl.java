package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.LoginVersionLog;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.LoginVersionLogMapper;
import com.zhidianfan.pig.yd.moduler.common.service.ILoginVersionLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户登录端版本号记录表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2018-12-17
 */
@Service
public class LoginVersionLogServiceImpl extends ServiceImpl<LoginVersionLogMapper, LoginVersionLog> implements ILoginVersionLogService {

}
