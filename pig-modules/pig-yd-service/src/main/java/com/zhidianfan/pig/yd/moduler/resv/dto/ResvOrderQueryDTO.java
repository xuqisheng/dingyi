package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: huzp
 * @Date: 2018/11/9 11:13
 * @DESCRIPTION 门店后台预订单查询dto
 */
@ApiModel(value="门店后台预订单查询dto")
@Data
public class ResvOrderQueryDTO {

    /**
     * 酒店id
     */
    @ApiModelProperty(value="酒店id")
    @NotNull(message = "酒店ID不存在")
    private Integer businessId;
    /**
     * 客户姓名
     */
    @ApiModelProperty(value="客户姓名")
    private String vipName;

    /**
     * 客户手机号码
     */
    @ApiModelProperty(value="客户电话")
    private String vipPhone;

    /**
     * 订单桌位区域id
     */
    @ApiModelProperty(value="桌位区域id")
    private Integer tableAreaId;


    /**
     * 餐别id
     */
    @ApiModelProperty(value="餐别id")
    private Integer mealTypeId;

    /**
     * 订单状态
     */
    @ApiModelProperty(value="订单状态")
    private String status;

    /**
     * 预定日期
     */
    @ApiModelProperty(value="预定日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private String resvDate;



    /**
     * 就餐开始时间
     */
    @ApiModelProperty(value="就餐开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date startTime;


    /**
     * 就餐结束时间
     */
    @ApiModelProperty(value="就餐结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date endTime;


    @ApiModelProperty(value="订单创建开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date createdAtStart;

    @ApiModelProperty(value="订单创建結束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date createdAtEnd;


}
