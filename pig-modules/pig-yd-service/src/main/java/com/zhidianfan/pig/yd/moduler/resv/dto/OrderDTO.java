package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-15
 * @Time: 09:54
 */
@Data
public class OrderDTO {

    /**
     * 酒店id
     */
    private Integer businessId;
    /**
     * 餐区id
     */
    private Integer tableAreaId;
    /**
     * 状态 1 已预订 2已入座 3已结账 4已退订 5锁定 6解锁
     */
    private List<Integer> status;

    /**
     * 桌位id
     */
    private Integer tableId;


    /**
     * 预定时间大于等于的条件
     */
    @ApiModelProperty("预定时间 大于等于的条件")
    private Date geResvDate;

}
