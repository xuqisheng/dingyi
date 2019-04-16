package com.zhidianfan.pig.yd.moduler.meituan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: hzp
 * @Date: 2019-04-11 09:36
 * @Description: yd公众号第三方订单
 */
@Data
public class PublicOrderDTO {

    /**
     * 易订平台客户下单
     *
     * {
     *  "businessId" 30 //酒店id
     * 	"status": 10,  //状态为 预定
     * 	"resvDate": 2018-12-10 10:00:00  // 预定时间
     * 	"number": 3,  //人数
     * 	"name": "李",  //客户姓名
     * 	"gender": "10", //客户性别  10 先生  20女士
     * 	"phone": "13676520623",  // 客户手机号码
     * 	"tableType": 0,  // 0 大厅  1包厢
     * 	"tableTypeName": "大厅",  // 大厅或者包厢
     * 	"openId": "o_DDX5coMSocvfeNsrpp86PXXy-8" //openid
     * }
     */
    private int businessId;
    private int status;
    /**
     * 预定日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date resvDate;
    private int number;
    private String name;
    private int gender;
    private String phone;
    private int tableType;
    private String tableTypeName;
    private String openId;




}
