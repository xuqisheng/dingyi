package com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lengleng
 * @date 2017/11/5
 */
@Data
public class UserDTO extends SysUser {
    /**
     * 角色ID
     */
    private List<Integer> role;

    private Integer deptId;

    /**
     * 新密码
     */
    private String newpassword1;
}
