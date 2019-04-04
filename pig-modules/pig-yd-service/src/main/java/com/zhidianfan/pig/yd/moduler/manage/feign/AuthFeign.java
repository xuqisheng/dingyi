package com.zhidianfan.pig.yd.moduler.manage.feign;

import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.yd.moduler.manage.dto.TipCommon;
import com.zhidianfan.pig.yd.moduler.manage.dto.UserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/27
 * @Modified By:
 */
@FeignClient(name = "pig-yd-user",fallbackFactory = AuthFeignFactory.class)
public interface AuthFeign {

    @PostMapping("/user/createAdmin")
    Tip createUserAdmin(@RequestBody UserDTO userDTO);


    @PostMapping("/user/create")
    SuccessTip createUser(@RequestBody UserDTO userDTO);

    @GetMapping("/user/validUserNum")
    int findUserNum(@RequestParam("username")String username,@RequestParam("clientId")String clientId);

    @PostMapping("/user/update")
    SuccessTip updateUser(@RequestBody UserDTO userDTO);

    /**
     * 手机号是否存在
     * @param mobile
     * @return
     */
    @PostMapping("/user/find/exist")
    SuccessTip userFindExist(@RequestParam("mobile") String mobile);

    /**
     * 更新手机号所有端密码
     * @param mobile
     * @param password
     * @return
     */
    @PostMapping("/user/update/password")
    SuccessTip updateUserPassword(@RequestParam("mobile")String mobile,@RequestParam("password")String password);


    /**
     * 更新账号所有端密码
     */
    @PostMapping("/user/update/password/account")
    SuccessTip updateUserPasswordAccount(@RequestParam("account")String account,@RequestParam("password")String password,@RequestParam("oldPassword")String oldPassword);


    @PostMapping("/user/delete")
    SuccessTip deleteUser(@RequestParam("username")String username,@RequestParam("clientId")String clientId);

    @PostMapping("/user/addUserRole")
    SuccessTip addUserRole(@RequestParam("username")String username,@RequestParam("roleName")String roleName);


    @PostMapping("/user/delUserRole")
    SuccessTip delUserRole(@RequestParam("username")String username,@RequestParam("roleName")String roleName);

    /**
     * 检查用户名、密码、客户端类型正确性
     * @param username
     * @param password
     * @param clientType
     * @return
     */
    @PostMapping("/user/check")
    TipCommon checkAuth(@RequestParam(name = "username") String username
            , @RequestParam(name = "password") String password
            , @RequestParam(name = "clientType") String clientType);

    @GetMapping("/user/find/phone")
    TipCommon findUserPhone(@RequestParam("username") String username, @RequestParam("clientType") String clientType);

}
