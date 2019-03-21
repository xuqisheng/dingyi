package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author LJH
 * @version 1.0
 * @Date 2018/9/19 13:29
 */
@Data
public class DeskOrderDTO {


    /**
     * 酒店id
     */
    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    /**
     * 预定日期
     */
    @NotNull(message = "到店日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date resvDate;

    /**
     * 餐别ID
     */
    @NotNull(message = "餐别ID不能为空")
    private Integer mealTypeId;

    /**
     * 区域
     */
    private Integer tableAreaId;

    /**
     *就餐人数 （预定查询时递交）
     */
    private String resvNum;

    /**
     * 状态 订单状态（1:已预约，2、已入座，3、已结账，4、已退订 ）
     */
    @ApiModelProperty("订单状态（1:已预约，2、已入座，3、已结账，4、已退订 ）")
    private String status;

    /**
     * 当前时间
     */
    @ApiModelProperty("不要填")
    private Date now;

    /**
     * 是否是会员
     */
    @ApiModelProperty("是否是会员")
    private Boolean isVip;


    /**
     * 搜索关键字
     */
    @ApiModelProperty("搜索关键字")
    private String keyword;


    @ApiModelProperty("是否评论")
    private Boolean isComment;


    @ApiModelProperty("订单时间 大于等于的条件")
    private Date geResvDate;

    @ApiModelProperty("订单时间 小于等于的条件")
    private Date leResvDate;


    @ApiModelProperty("批次号")
    private String batchNo;




}
