package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: hzp
 * @Date: 2019-06-17 14:08
 * @Description:
 */
@Data
public class PerformanceDTO {

    /**
     * 酒店id
     */
    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    /**
     * 预定日期开始时间
     */
    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date startTime;


    /**
     * 预定日期 结束时间
     */
    @NotNull(message = "结束日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date endTime;


    /**
     * 筛选条件
     * 1. 消费
     * 2. 退订
     */
    private Integer status;



}
