package com.zhidianfan.pig.yd.sms.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 通道配置表基本属性加当前阈值
 */
@Data
public class Channel {

    private Long id;
    /**
     * 运营商名称
     */
     
    private String operatorName;

     
    private String beanName;
    /**
     * 是否为默认通道 0-否,1-是
     */
     
    private Integer defaultOperator;
    /**
     * 阈值 百分制 当前通道处于正常状态时，其短信到达率的最低值
     */
    private String threshold;
    /**
     * 人工设置通道是否可用 0-不可用 1 - 可用
     */
    private Integer status;
    /**
     * 人工干预开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date startTime;
    /**
     * 人工干预结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date endTime;

    /**
     * 计算周期,按天技术,N天内发送情况
     */
    private Integer calPeriod;


    /**
     * 当前计算阈值
     */
    private Double currSucRate;


}
