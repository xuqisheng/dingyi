package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: hzp
 * @Date: 2019-03-29 11:01
 * @Description: 客户关怀
 */
@Getter
@Setter
public class CustomerCareDTO {

    private String title ;
    private String name;
    private String phone;
    private String customerValue;
    private Integer customerValueId;
    private String date;
    private String surplusTime;
    private String vipId;
    private String businessId;

    /**
     * 日期类型 : 0 纪念日
     *          1 生日
     */
    private Integer type;
    /**
     * 1.如果为纪念日则是纪念日id
     * 2.如果是生日则是客户id
     */
    private String calendarId;



}
