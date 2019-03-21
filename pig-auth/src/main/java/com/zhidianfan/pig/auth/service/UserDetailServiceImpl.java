/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.zhidianfan.pig.auth.service;

import com.zhidianfan.pig.auth.feign.UserService;
import com.zhidianfan.pig.auth.util.PasswordUtils;
import com.zhidianfan.pig.auth.util.UserDetailsImpl;
import com.zhidianfan.pig.common.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lengleng
 * @date 2017/10/26
 * <p>
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询pig.sys_user表
        UserVO userVo = userService.findUserByUsername(username);

        if (userVo == null) {
            log.info(username + "不存在pig系统中，使用admin账号获取token");
            userVo = userService.findUserByUsername("admin");
        }

        try {

            String pd = request.getParameter("password");
            String auth = request.getHeader("Authorization");
            log.info("{},当前用户名 {} 对应的密码 {}", auth, username, pd);
            userVo.setPassword(PasswordUtils.encode(pd));

            log.info("收到设置密码完全正确");
        } catch (Exception e) {
            log.info("获取用户密码失败");
        }


        return new UserDetailsImpl(userVo);
    }
}
