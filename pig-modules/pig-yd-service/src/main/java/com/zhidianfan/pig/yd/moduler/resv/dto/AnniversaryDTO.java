package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: hzp
 * @Date: 2019-03-28 16:39
 * @Description: 纪念日dto
 */

@Getter
@Setter
public class AnniversaryDTO {

    /**
     * 纪念日id
     */
    private Integer id;
    /**
     * 客户id
     */
    private Integer vipId;
    /**
     * 纪念日日期
     */
    private Date anniversaryDate;
    /**
     * 农历公历表示 0 公历  1 农历
     */
    private Integer calendarType;
    /**
     * 纪念日对象
     */
    private String anniversaryObj;
    /**
     * 酒店id
     */
    private Integer businessId;
    /**
     * 是否隐藏年份 0不隐藏 1隐藏
     */
    private Integer anniversaryYearFlag;
    /**
     * 纪念日备注
     */
    private String remark;
    /**
     * 纪念日图片url 多张图片url 可以用逗号隔开
     */
    private String anniversaryPic;

    /**
     * 纪念日标题
     */
    private String anniversaryTitle;

    /**
     * 距离下一次纪念日天数
     */
    private Integer surplusDay;

    /**
     * 周年数
     */
    private Integer yearsNumber;

    /**
     * 展示的日期
     */
    private String dateOfDisplay;


}
