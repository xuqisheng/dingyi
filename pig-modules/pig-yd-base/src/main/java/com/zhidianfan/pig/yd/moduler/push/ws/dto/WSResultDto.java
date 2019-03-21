package com.zhidianfan.pig.yd.moduler.push.ws.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询指定设备 deviceType 注册信息 DTO
 * @author sjl
 * 2019-03-01 16:54
 */
@Data
public class WSResultDto implements Serializable {
    /**
     * 设备类型 Id,取值 1-
     * 1：安卓电话机
     * 2. 小程序
     * 3. pad 预订台
     */
    String deviceType;

    /**
     * 小程序唯一 id
     */
    String openid;
}
