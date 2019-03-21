package com.zhidianfan.pig.yd.moduler.resv.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @Author: huzp
 * @Date: 2018/9/25 13:21
 */
@Data
public class LineDTO {

    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    @NotBlank(message = "酒店名字不能为空")
    private String businessName;

    /**
     * 客户id
     */
    private Integer vipId;
    /**
     * 客户电话
     */
    private String vipPhone;

    private String vipName;
    /**
     * 客户性别
     */
    private String vipSex;

    private String resvTime;

    /**
     * 预定人数
     */
    @NotNull(message = "预订人数不能为空")
    private Integer resvNum;
    /**
     * 预定日期
     */
    @NotNull(message = "预定日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date resvDate;
    /**
     * 用餐时段id
     */
    @NotNull(message = "预订餐别不能为空")
    private Integer mealTypeId;
    /**
     * 用餐时段name
     */
    private String mealTypeName;


    private String remark;


    /**
     * 外部来源
     */
    private Integer resvSourceId;

    private String resvSourceName;

    /**
     * 是否发送短信
     */
    private Integer issendmsg;


    /**
     * 使用id
     */
    private Integer appUserId;

    private String appUserName;

    /**
     *设备id
     */
    private Integer deviceUserId;

    private String deviceUserName;


    /**
     * 安卓电话机
     */
    private Integer androidUserId;

    private String androidUserName;
}
