package com.zhidianfan.pig.user.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.user.dao.entity.YdUser;
import com.zhidianfan.pig.user.dao.entity.YdUserRole;
import com.zhidianfan.pig.user.dao.service.IYdRoleService;
import com.zhidianfan.pig.user.dao.service.IYdUserRoleService;
import com.zhidianfan.pig.user.dao.service.IYdUserService;
import com.zhidianfan.pig.user.dto.UserDTO;
import com.zhidianfan.pig.user.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Resource
    private IYdUserService iYdUserService;

    /**
     * 判断出用户名密码正确性
     *
     * @param username
     * @param password
     * @param clientType
     * @return
     */
    @PostMapping("/user/check")
    public ResponseEntity checkAuth(@RequestParam(name = "username") String username
            , @RequestParam(name = "password") String password
            , @RequestParam(name = "clientType") String clientType) {
        Tip tip = new SuccessTip(200, "校验通过");

        //检查密码正确性
        boolean b = authService.checkAuth(username, password, clientType);
        if (!b) {
            tip = new ErrorTip(400, "用户名密码校验失败");
        }
        return ResponseEntity.ok(tip);
    }

    @PostMapping("/user/check/v2")
    public ResponseEntity checkAuthV2(@RequestParam(name = "username") String username
            , @RequestParam(name = "clientType") String clientType) {
        Tip tip = new SuccessTip(200, "校验通过");

        //检查密码正确性
        boolean b = authService.checkAuthV2(username, clientType);
        if (!b) {
            tip = new ErrorTip(400, "用户名密码校验失败");
        }
        return ResponseEntity.ok(tip);
    }

    @GetMapping("/user/find/phone")
    public ResponseEntity findUserPhone(@RequestParam("username")String username,@RequestParam("clientType") String clientType){
        YdUser user = authService.findUser(username, clientType);
        if(user == null){
            return ResponseEntity.badRequest().body(ErrorTip.ERROR_TIP);
        }
        SuccessTip successTip = new SuccessTip();
        successTip.setMsg(user.getPhone());
        return ResponseEntity.ok(successTip);
    }

    /**
     * 检查用户名与url匹配性
     *
     * @param username
     * @param url
     * @return
     */
    @GetMapping("/user/check/usernameurl")
    public Tip checkUsernameUrl(@RequestParam String username, @RequestParam String url) {
        Tip tip = new SuccessTip(200, "校验通过");
        boolean b = authService.checkUsernameUrl(username, url);
        if (!b) {
            tip = new ErrorTip(400, "无权限");
        }
        return tip;
    }

    /**
     * 检查角色名与url匹配性
     *
     * @param role
     * @param url
     * @return
     */
    @GetMapping("/user/check/roleurl")
    public Tip checkRoleUrl(@RequestParam String role, @RequestParam String url) {
        Tip tip = new SuccessTip(200, "校验通过");
        boolean b = authService.checkRoleUrl(role, url);
        if (!b) {
            tip = new ErrorTip(400, "无权限");
        }
        return tip;
    }

    @PostMapping("/user/createAdmin")
    public Tip createAdminUser(@RequestBody UserDTO userDTO){
        Tip tip = authService.createAdminUser(userDTO);
        return tip;
    }

    @PostMapping("/user/find/exist")
    public Tip userFindExist(@RequestParam("mobile") String mobile){
        Tip tip = authService.userFindExist(mobile);
        return tip;
    }

    @PostMapping("/user/create")
    public ResponseEntity createUser(@RequestBody UserDTO userDTO){
        Tip tip = authService.createUser(userDTO);
        return ResponseEntity.ok(tip);
    }

    @PostMapping("/user/update")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO){
        Tip tip = authService.updateUser(userDTO);
        return ResponseEntity.ok(tip);
    }

    @ApiOperation("更新账户密码根据手机号码  所有端统一更改")
    @PostMapping("/user/update/password")
    public ResponseEntity updateUserPassword(@RequestParam("mobile") String mobile, @RequestParam("password") String password){
        Tip tip = authService.updatePasswordUser(mobile,password);
        return ResponseEntity.ok(tip);
    }


    @ApiOperation("更新账户密码根据账号  所有端统一更改")
    @PostMapping("/user/update/password/account")
    public ResponseEntity updateUserPasswordAccount(@RequestParam("account") String account, @RequestParam("password") String password, String oldPassword){

        YdUser condition = new YdUser();
        condition.setUsername(account);

        if(StringUtils.isNotBlank(oldPassword)) {
            condition.setPassword(oldPassword);
            YdUser user = iYdUserService.selectOne(new EntityWrapper<>(condition));
            if (user == null) {
                return ResponseEntity.ok(new ErrorTip(4001, "旧密码错误"));
            }
        }

        condition.setPassword(null);
        YdUser newPassword = new YdUser();
        newPassword.setPassword(password);
        newPassword.setUpdateTime(new Date());
        boolean b = iYdUserService.update(newPassword,new EntityWrapper<>(condition));
        return ResponseEntity.ok(b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP);
    }

    /**
     * 根据条件查找有效用户数量
      * @return
     */
    @GetMapping("/user/validUserNum")
    public Integer validUserNum(@RequestParam("username") String username,@RequestParam("clientId") String clientId){
        YdUser ydUser = new YdUser();
        ydUser.setClientId(clientId);
        ydUser.setUsername(username);
        ydUser.setDelFlag("0");
        int i = iYdUserService.selectCount(new EntityWrapper<>(ydUser));
        return i;
    }



    @PostMapping("/user/delete")
    public Tip createUser(@RequestParam("username") String username,@RequestParam("clientId") String clientId){
        boolean b = authService.deleteUser(username, clientId);
        return b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP;
    }

    @PostMapping("/user/addUserRole")
    public Tip addUserRole(@RequestParam("username") String username,@RequestParam("roleName") String roleName){
        boolean b = authService.installUserRole(username, roleName);

        return b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP;
    }

    @PostMapping(value = "/user/delUserRole",params = {"username","roleName"})
    public Tip delUserRole(@RequestParam("username") String username,@RequestParam("roleName") String roleName){
        boolean b = authService.delUserRole(username, roleName);

        return b?SuccessTip.SUCCESS_TIP:ErrorTip.ERROR_TIP;
    }



}
