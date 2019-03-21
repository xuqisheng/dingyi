package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.common.util.UserUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AndroidUserInfo;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.manage.dto.TipCommon;
import com.zhidianfan.pig.yd.moduler.manage.feign.AuthFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.AndroidUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.Locale;

/**
 * @Author: huzp
 * @Date: 2018/9/18 15:53
 */
@RestController
@RequestMapping("/androidUser")
@Slf4j
public class AndroidUserInfoController {

    @Autowired
    private AndroidUserInfoService androidUserInfoService;

    @Autowired
    private MessageSource messageSource;

    @Resource
    private AuthFeign authFeign;


    /**
     * 获取用户信息
     *
     * @return 查询成功则返回用户信息，失败则返回提示信息
     */
    @GetMapping("/info")
    public ResponseEntity getAndroidUserInfo(HttpServletRequest request) {

        String username ;
        try {
            username = URLDecoder.decode(request.getHeader("auth_username"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            ErrorTip tip = new ErrorTip();
            tip.setCode(500);
            tip.setMsg("账号解析错误");
            return ResponseEntity.ok(tip);
        }
        String password = request.getHeader("auth_password");
        String client = request.getHeader("auth_client_type");

        //判断用户名和密码
        TipCommon tipCommon = authFeign.checkAuth(username, password, client);
        if (200 != tipCommon.getCode()) {
            return ResponseEntity.ok(new ErrorTip(401,"账号或密码错误"));
        }

        //解析token获取loginName
        String nameTest = UserUtils.getUserName();

        log.info("通过UserUtils与request获取到的username分别为:\n{} : {}",username,nameTest);

        //获取用户信息
        AndroidUserInfo userInfo = androidUserInfoService.getAndroidUserInfo(username);

        //用户信息查询失败判断
        if (null == userInfo) {
            ErrorTip tip = new ErrorTip();
            tip.setCode(500);
            //国际化
            Locale locale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage("uiaf", null, locale);
            tip.setMsg(msg);
            return ResponseEntity.ok(tip);
        }

        return ResponseEntity.ok(userInfo);
    }
}
