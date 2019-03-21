package com.zhidianfan.pig.yd.moduler.resv.service.rmi;

import com.zhidianfan.pig.common.util.R;
import com.zhidianfan.pig.common.vo.UserVO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.UserDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback.SysUserFeignFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/8/28
 * @Modified By:
 */
@FeignClient(name = "pig-upms-service", /*fallback = SysUserFeignImpl.class,*/ fallbackFactory = SysUserFeignFactory.class)
public interface SysUserFeign {

    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping("/user/findUserByUsername/{username}")
    UserVO findUserByUsername(@PathVariable("username") String username);

    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @DeleteMapping("/user/{id}")
    R<Boolean> userDel(@PathVariable("id") Integer id);


    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @PostMapping("/user")
    R<Boolean> user(@RequestBody UserDTO userDto);

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @PutMapping("/user")
    R<Boolean> userUpdate(@RequestBody UserDTO userDto);

    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @param userVo  登录用户信息
     * @return success/false
     */
    @PutMapping("/user/editInfo")
    R<Boolean> editInfo(@RequestBody UserDTO userDto, @RequestParam("userVo") UserVO userVo);

}
