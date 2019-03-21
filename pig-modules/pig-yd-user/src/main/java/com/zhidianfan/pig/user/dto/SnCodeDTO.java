package com.zhidianfan.pig.user.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/21
 * @Modified By:
 */
@Data
public class SnCodeDTO {

    private Long id;
    /**
     * 设备码
     */
    private String code;
    /**
     * 经销商id
     */
    private Long agentId;

    private String agentName;
    /**
     * 酒店id
     */
    private Long businessId;

    private String businessName;
    /**
     * 0 未激活1激活 2停用 3维修
     */
    private Integer status;

    private int page;

    private int rows;

}
