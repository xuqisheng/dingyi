package com.zhidianfan.pig.user.dao.service.impl;

import com.zhidianfan.pig.user.dao.entity.YdUser;
import com.zhidianfan.pig.user.dao.mapper.YdUserMapper;
import com.zhidianfan.pig.user.dao.service.IYdUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表,记录全局用户的基本认证信息 服务实现类
 * </p>
 *
 * @author qqx
 * @since 2018-11-07
 */
@Service
public class YdUserServiceImpl extends ServiceImpl<YdUserMapper, YdUser> implements IYdUserService {

}
