package com.zhidianfan.pig.yd.moduler.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AndroidUserInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AppUser;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.AndroidUserInfoMapper;
import com.zhidianfan.pig.yd.moduler.common.dao.mapper.AppUserMapper;
import com.zhidianfan.pig.yd.moduler.common.service.IAndroidUserInfoService;
import com.zhidianfan.pig.yd.moduler.common.service.IAppUserService;
import org.springframework.stereotype.Service;

/**
 * @Author: huzp
 * @Date: 2018/9/20 10:22
 */
@Service
public class AndroidUserInfoServiceImpl extends ServiceImpl<AndroidUserInfoMapper, AndroidUserInfo> implements IAndroidUserInfoService {

}
