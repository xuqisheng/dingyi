package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AndroidUserInfo;
import com.zhidianfan.pig.yd.moduler.common.service.IAndroidUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: huzp
 * @Date: 2018/9/20 10:08
 */
@Service
public class AndroidUserInfoService {

    @Autowired
    private IAndroidUserInfoService iAndroidUserInfoService;

    /**
     *  获取用户信息
     * @param loginName 用户登录名
     * @return 返回用户信息
     */
    public AndroidUserInfo getAndroidUserInfo(String loginName) {

        AndroidUserInfo userInfo = iAndroidUserInfoService.selectOne(new EntityWrapper<AndroidUserInfo>()
                .eq("login_name", loginName));

        return userInfo;
    }

}
