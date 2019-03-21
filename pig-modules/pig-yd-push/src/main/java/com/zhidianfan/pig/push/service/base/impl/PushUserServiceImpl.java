package com.zhidianfan.pig.push.service.base.impl;

import com.zhidianfan.pig.push.dao.entity.PushUser;
import com.zhidianfan.pig.push.dao.mapper.PushUserMapper;
import com.zhidianfan.pig.push.service.base.IPushUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljh
 * @since 2018-11-02
 */
@Service
public class PushUserServiceImpl extends ServiceImpl<PushUserMapper, PushUser> implements IPushUserService {

}
