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

package com.zhidianfan.pig.common.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.zhidianfan.pig.common.constant.CommonConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.List;

/**
 * @author lengleng
 * @date 2017/11/20
 * 用户相关工具类
 */
@Slf4j
public class UserUtils {
    private static final ThreadLocal<String> THREAD_LOCAL_USER = new TransmittableThreadLocal<>();
    private static final String KEY_USER = "user";

    /**
     * 获取当前客户端标记
     *
     * @return
     */
    public static String getClientId() {

        log.info("try to getClientId");

        String s1 = getAuthclienttype();
        if (StringUtils.isNotBlank(s1)) {
            return s1;
        }

        String token = getToken();

        if (StringUtils.isBlank(token)) {
            return "SYSTEM";
        }

        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        String clientId = (String) claims.get("client_id");
        return clientId;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName() {

        log.info("try to getUserName");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURI();
        String token = getToken();
        String s1 = "";

        if (StringUtils.isBlank(token) && "/auth/oauth/token".equals(url)) {
            try {
                s1 = URLDecoder.decode(request.getParameter("auth_username"),"utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("账号格式错误");
            }
            return s1;
        }
        s1 = getAuthusername();
        if (StringUtils.isNotBlank(s1)) {
            return s1;
        }


        if (StringUtils.isBlank(token)) {
            return "sysuser";
        }

        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        String username = (String) claims.get("user_name");
        return username;
    }


    /**
     * 根据请求heard中的token获取用户角色
     *
     * @param httpServletRequest request
     * @return 角色名
     */
    public static List<String> getRole(HttpServletRequest httpServletRequest) {
        String token = getToken(httpServletRequest);
        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        List<String> roleNames = (List<String>) claims.get("authorities");
        return roleNames;
    }

    /**
     * 根据header中的token获取用户ID
     *
     * @param httpServletRequest
     * @return 用户ID
     */
    public static Integer getUserId(HttpServletRequest httpServletRequest) {
        String token = getToken(httpServletRequest);
        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId;
    }

    /**
     * 获取请求中token
     *
     * @param httpServletRequest request
     * @return token
     */
    public static String getToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(CommonConstant.REQ_HEADER);
        return StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT);
    }

    /**
     * 设置用户信息
     *
     * @param username 用户名
     */
    public static void setUser(String username) {
        THREAD_LOCAL_USER.set(username);
        MDC.put(KEY_USER, username);
    }

    /**
     * 从threadlocal 获取用户名
     *
     * @return 用户名
     */

    public static String getUser() {
        return THREAD_LOCAL_USER.get();
    }

    public static void clearAllUserInfo() {
        THREAD_LOCAL_USER.remove();
        MDC.remove(KEY_USER);
    }

    private static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = UserUtils.getToken(request);
        log.info("get token:{}", token);
        return token;
    }

    private static String getAuthusername() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username =null;
        try {
            String auth_username = request.getParameter("auth_username");
            if(StringUtils.isNotBlank(auth_username))
                username = URLDecoder.decode(auth_username,"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("账号格式错误");
        }
        return username;
    }

    private static String getAuthpassword() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String password = request.getHeader("auth_password");
        return password;
    }

    //auth_client_type
    private static String getAuthclienttype() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String auth_client_type = request.getHeader("auth_client_type");
        return auth_client_type;
    }


}
