package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *  订单查询条件
 * @author LJH
 * @version 1.0
 * @Date 2018/9/21 11:04
 */
@Data
public class OrderConditionDTO {


    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    @NotBlank(message = "手机号码不能为空")
    private String vipPhone;

    /**
     * 预定日期
     */
    private Date resvDate;


    /**
     * 餐别
     */
    private Integer mealTypeId;


    /**
     * 订单状态
     */
    private String status;
}
