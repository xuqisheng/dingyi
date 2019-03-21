package com.zhidianfan.pig.user.dto;

import lombok.Data;

import java.util.Date;


/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Data
public class AgentDTO {

    /**
     * 代理商id
     */
    private Long id;
    /**
     * 微信openid
     */
    private String openId;
    /**
     * 代理商名称
     */
    private String agentName;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 1 独家城市代理 2普通
     */
    private Integer agentLevel;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 所属省份id
     */
    private Integer provinceId;
    /**
     * 所属城市id
     */
    private Integer cityId;
    private Integer areaId;
    private String provinceName;
    private String cityName;
    private String areaName;
    /**
     * 代理商所属类型(0个人、1公司、2自销)
     */
    private Integer type;
    /**
     * 代理商地址
     */
    private String agentAddress;
    /**
     * 代理商手机号
     */
    private String agentPhone;
    /**
     * 营业执照url
     */
    private String licenceUrl;
    /**
     * 新增时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;
    /**
     * 联系人名称
     */
    private String agentManager;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 0 待审核 1提供 2拒绝 3停用
     */
    private Integer status;
    /**
     * 审批时间
     */
    private Date reviewTime;
    /**
     * 审批人
     */
    private String reviewUser;
    private String tag;
    private Integer oldStatus;

    private int page;

    private int rows;
}
