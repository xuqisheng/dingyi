package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author LJH
 * @version 1.0
 * @Date 2018/9/25 10:32
 */
@ApiModel(value="排队订单查询条件")
@Data
public class LineQueryDTO {

    @ApiModelProperty(value="酒店id")
    @NotNull
    private Integer businessId;

    /**
     * 预定日期
     */
    @ApiModelProperty(value="预定日期")
    private Date resvDate;

    /**
     * 客户姓名
     */
    @ApiModelProperty(value="客户姓名")
    private String vipName;


    /**
     * 客户手机号
     */
    @ApiModelProperty(value="客户手机号")
    private String vipPhone;


    /**
     * 餐别
     */

    private Integer mealTypeId;


    /**
     * 排序字段（line_sort 排队号、resv_time 到店时间） 顺序
     */
    private String orderByField;


    /**
     * 搜索字段（客户手机号码  姓名  桌位名称）
     */
    private String search;

    /**
     * 0正在排队  1排队转入座后改  2取消排队
     */
    private Integer status;

    private String lineNo;

    /**
     * 就餐开始时间
     */
    @ApiModelProperty(value="就餐开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date startTime;


    /**
     * 就餐结束时间
     */
    @ApiModelProperty(value="就餐结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date endTime;



    /**
     * 排队表格筛选条件 排队状态  待转订单 , 已转订单,  已失效
     */
    @ApiModelProperty(value="排队状态:  待转订单 , 已转订单,  已失效")
    private String lineStatus;

    /**
     * 安卓电话机用户信息
     */
    @ApiModelProperty(value="安卓用户id")
    private Integer androidUserId;

    @ApiModelProperty(value="安卓用户名")
    private String androidUserName;
}
