package com.zhidianfan.pig.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/27
 * @Modified By:
 */
@Data
public class UserDTO {

    /**
     * 客户端标识

     ANDROID_PHONE - Android电话机
     WEB_MANAGER - 新版网页后台
     */
    private String clientId;
    /**
     * 用户名
     */
    private String username;
    private String password;
    /**
     * 多个使用英文逗号分隔
     */
    private String phone;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 0-正常，1-删除
     */
    private String delFlag;


    private String roleName;
}
