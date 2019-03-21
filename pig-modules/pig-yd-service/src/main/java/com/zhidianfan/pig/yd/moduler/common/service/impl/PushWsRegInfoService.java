package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.PushWsRegInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.PushWsRegInfoMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IPushWsRegInfoService;
import org.springframework.stereotype.Service;

/**
 * Websockt 推送注册信息表 service层
 *
 * @author hzp
 * @date 2019-03-06 07:47:52
 */
@Service
public class PushWsRegInfoService extends ServiceImpl<PushWsRegInfoMapper, PushWsRegInfo> implements IPushWsRegInfoService {

}
