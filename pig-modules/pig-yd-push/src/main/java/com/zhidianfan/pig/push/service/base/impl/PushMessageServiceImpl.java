package com.zhidianfan.pig.push.service.base.impl;

import com.zhidianfan.pig.push.dao.entity.PushMessage;
import com.zhidianfan.pig.push.dao.mapper.PushMessageMapper;
import com.zhidianfan.pig.push.service.base.IPushMessageService;
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
public class PushMessageServiceImpl extends ServiceImpl<PushMessageMapper, PushMessage> implements IPushMessageService {

}
