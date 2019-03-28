package com.zhidianfan.pig.yd.moduler.meituan.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/16
 * @Modified By:
 */
@Data
public class KbOrderDTO {

    /**
     * resvDate : 2018-11-16 15:30
     * vipName : 1
     * resvNum : 1
     * vipPhone : 13777575146
     * mealTypeId : 9
     * mealTypeName : 午餐
     * vipSex : 男
     * businessName : 鼎壹大酒店
     * businessId : 30
     * remark :
     * createTime : 2018-11-16 14:20
     */

    private Date resvDate;
    private String vipName;
    private Integer resvNum;
    private String vipPhone;
    private String vipSex;
    private String businessName;
    private Integer businessId;
    private String remark;
    private Integer tableType;
    private String tableTypeName;

}
