package com.zhidianfan.pig.yd.moduler.manage.dto;

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
    private Integer id;
    private String openId;
    private String agentName;
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
    /**
     * 所属省份
     */
    private String provinceName;
    /**
     * 所属城市
     */
    private String cityName;
    /**
     * 代理商所属类型(0个人、1公司、2自销)
     */
    private Integer type;
    /**
     * 地址
     */
    private String address;
    /**
     * 代理商手机号
     */
    private String phoneNum;
    /**
     * 营业执照url
     */
    private String licenceUrl;
    /**
     * 0 待审核 1提供 2拒绝 3停用
     */
    private String status;
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
    private String linkman;
    /**
     * 结算价格
     */
    private String settlementPrice;
    private String remarks;
    private String username;
    private String password;
    private Integer device;
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
