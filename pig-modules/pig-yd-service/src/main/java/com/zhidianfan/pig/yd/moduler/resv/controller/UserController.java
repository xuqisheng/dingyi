package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.util.PasswordUtils;
import com.zhidianfan.pig.yd.moduler.common.constant.ClientId;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.AndroidUserInfo;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsValidate;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IAndroidUserInfoService;
import com.zhidianfan.pig.yd.moduler.common.service.ISmsValidateService;
import com.zhidianfan.pig.yd.moduler.manage.dto.TipCommon;
import com.zhidianfan.pig.yd.moduler.resv.dto.ChangePasswordDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private IAndroidUserInfoService androidUserInfoService;

    @Autowired
    private ISmsValidateService smsValidateService;

    /**
     * 认证信息同步(不开放外部调用)
     * 此接口只调用一次
     *
     * @return
     */
    @GetMapping("/auth")
    public ResponseEntity doAuth() {
        userAuthService.doAuthSync();
        return ResponseEntity.ok("等待异步执行结果");
    }

    /**
     * 各平台，新增用户注册/修改
     *
     * @param username
     * @param password
     * @param oldPassword
     * @param clientId
     * @return
     */
    @PostMapping(value = "/reg", params = {"username", "password", "clientId"})
    public ResponseEntity regNewUser(@RequestParam String username
            , @RequestParam String password
            , @RequestParam(required = false) String oldPassword
            , @RequestParam String phone
            , @RequestParam String clientId) {
        //检查客户端是否在范围内，如果不在，会抛出异常
        ClientId.valueOf(clientId);

        Tip tip = userAuthService.regUser(username, oldPassword, password, phone, clientId);

        return ResponseEntity.ok(tip);
    }


    /**
     * 各平台，新增用户注册/修改 V2版本
     * @param username
     * @param password
     * @param oldPassword
     * @param phone
     * @param clientId
     * @param roles
     * @param deptName
     * @param parentDeptName
     * @return
     */
    @PostMapping(value = "/reg/v2", params = {"username", "password", "clientId", "roles", "deptName"})
    public ResponseEntity regNewUserV2(@RequestParam String username
            , @RequestParam String password
            , @RequestParam(required = false) String oldPassword
            , @RequestParam String phone
            , @RequestParam String clientId
            , @RequestParam String roles
            , @RequestParam String deptName
            , @RequestParam String parentDeptName) {
        //检查客户端是否在范围内，如果不在，会抛出异常
        ClientId.valueOf(clientId);

        Tip tip = userAuthService.regUserV2(username, oldPassword, password, phone, clientId, roles, deptName, parentDeptName);

        return ResponseEntity.ok(tip);
    }

    /**
     * 是否存在
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/exists", params = {"username"})
    public ResponseEntity existsUser(@RequestParam String username) {
        Boolean exists = userAuthService.existsUser(username);
        return ResponseEntity.ok(exists);
    }
    /**
     * 是否存在
     *
     * @param username
     * @return
     */
    @PostMapping(value = "/del", params = {"username"})
    public ResponseEntity delUser(@RequestParam String username) {
        Boolean result = userAuthService.delUser(username);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/change/password")
    @Transactional
    public ResponseEntity changePassword(@Valid ChangePasswordDTO changePasswordDTO){
        TipCommon tipCommon = new TipCommon();
        if(!PasswordUtils.checkSimplePassword(changePasswordDTO.getPassword())){
            tipCommon.setCode(500);
            tipCommon.setMsg("密码过于简单");
            return ResponseEntity.badRequest().body(tipCommon);
        }

        AndroidUserInfo userInfo = androidUserInfoService.selectOne(new EntityWrapper<AndroidUserInfo>().eq("login_name", changePasswordDTO.getUsername()));
        if(null == userInfo){
            log.debug("用户名:{} 不存在",changePasswordDTO.getUsername());
            tipCommon.setCode(500);
            tipCommon.setMsg("用户不存在");
            return ResponseEntity.badRequest().body(tipCommon);
        }

        SmsValidate smsValidate = smsValidateService.selectOne(new EntityWrapper<SmsValidate>().eq("mobile", userInfo.getAppUserPhone()).eq("code", changePasswordDTO.getValidate()));

        if(null == smsValidate || smsValidate.getIsUse() == 1){
            log.debug("验证码错误");
            tipCommon.setCode(500);
            tipCommon.setMsg("验证码错误");
            return ResponseEntity.badRequest().body(tipCommon);
        }

        boolean flag = true;
        try {
            smsValidate.setIsUse(1);
            boolean smsValidateUpdate = smsValidateService.updateById(smsValidate);
            userInfo.setAppUserPassword(changePasswordDTO.getPassword());
            boolean userInfoUpdate = androidUserInfoService.updateById(userInfo);
            if(!smsValidateUpdate || !userInfoUpdate){
                flag = false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            flag = false;
        }

        if(!flag){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            tipCommon.setCode(500);
            tipCommon.setMsg("密码修改失败");
            return ResponseEntity.badRequest().body(tipCommon);
        }


        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

}
