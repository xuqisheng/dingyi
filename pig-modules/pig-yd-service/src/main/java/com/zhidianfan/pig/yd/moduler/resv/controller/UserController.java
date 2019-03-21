package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.constant.ClientId;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.resv.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAuthService userAuthService;

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

}
