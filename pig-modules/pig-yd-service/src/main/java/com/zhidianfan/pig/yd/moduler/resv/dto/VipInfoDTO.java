package com.zhidianfan.pig.yd.moduler.resv.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: huzp
 * @Date: 2018/9/29 15:44
 */
@ApiModel(value="客户查询条件")
@Data
public class VipInfoDTO {

    private Integer id;
    @ApiModelProperty(value="酒店id")
    private Integer businessId;
    private String businessName;
    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    private String vipName;
    /**
     * 客户电话
     */
    @ApiModelProperty(value="客户电话")
    private String vipPhone;

    /**
     * 客户手机2
     */
    private String vipPhone2;
    /**
     * 客户公司
     */
    private String vipCompany;
    /**
     * 客户性别
     */
    private String vipSex;
    /**
     * 客户生日
     */
    private String vipBirthday;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date updatedAt;

    private String vipAddress;
    /**
     * 备注
     */
    private String remark;
    /**
     * 短号
     */
    private String shortPhoneNum;
    /**
     * 固定电话
     */
    private String telephone;
    /**
     * 爱好
     */
    private String hobby;
    /**
     * 禁忌，厌恶
     */
    private String detest;
    /**
     * 中文全名拼音缩写 如姓名是张三 则存放 zs
     */
    private String spells;
    /**
     * 姓简拼
     */
    private String familyNameSpell;
    @ApiModelProperty(value="客户价值id")
    private Integer vipValueId;
    private String vipValueName;
    private Integer vipClassId;
    private String vipClassName;
    private Integer appUserId;
    private String imageUrl;
    /**
     * 标签
     */
    private String tag;
    /**
     * 农历日期
     */
    private String vipBirthdayNl;
    private Integer targetId;
    /**
     * 集团分类id
     */
    private Integer vipBrandClassId;
    /**
     * 集团分类名称
     */
    private String vipBrandClassName;


    /**
     * 预订次数
     */
    private Integer resvTimes;

    /**
     * 实际消费次数
     */
    @ApiModelProperty(value="就餐次数(几次以上)")
    private Integer actResvTimes;

    /**
     *
     */
    @ApiModelProperty(value="就餐次数(几次以内)")
    private Integer actResvTimesLt;

    /**
     * 预订桌数
     */
    private String tableNum;


    /**
     * 实际消费桌数
     */
    private String actTableNum;


    /**
     * 预订总人数
     */
    private String peopleNum;

    /**
     * 实际消费总人数
     */
    private String actPeopleNum;

    /**
     * 上次就餐时间
     */
    @ApiModelProperty(value="上次就餐时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date lastEatTime;


    @ApiModelProperty(value="上次就餐时间(多少天以外)")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date lastEatTimeLt;

    @ApiModelProperty(value="支付金额多少以上")
    private Integer paySum;
    @ApiModelProperty(value="支付金额多少以内")
    private Integer paySumLt;

    private String  vipPostion;

    private Integer birthFlag;

    private Integer hideBirthdayYear;

    private Date nextVipBirthday;

    private Integer isLeap;

    private String allergen;

}
