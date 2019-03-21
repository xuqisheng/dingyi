package com.zhidianfan.pig.yd.moduler.resv.dto;


import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipStatistics;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: 潘钱勇
 * @Date: 2018/11/15
 */
@Data
public class StatisticsVipDTO {


    /**
     * vipId
     */
    private Integer vipId;
    /**
     * 客户名称
     */
    private String vipName;
    /**
     * 客户电话
     */
    private String vipPhone;
    /**
     * 用户价值id
     */
    private Integer vipValueId;
    /**
     * 用户价值name
     */
    private String vipValueName;

    /**
     * 预约次数
     */
    private Integer resvBatchCount;
    /**
     * 预约桌数
     */
    private Integer resvOrderCount;
    /**
     * 预约人数
     */
    private Integer resvPeopleNum;
    /**
     * 就餐次数
     */
    private Integer mealBatchCount;
    /**
     * 就餐桌数
     */
    private Integer mealOrderCount;
    /**
     * 就餐人数
     */
    private Integer mealPeopleNum;
    /**
     * 最近就餐日期
     */
    private Date lastMealDate;


}
