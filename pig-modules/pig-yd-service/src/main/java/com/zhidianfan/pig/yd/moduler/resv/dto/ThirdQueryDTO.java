package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: huzp
 * @Date: 2018/10/12 15:22
 */
@Data
public class ThirdQueryDTO {
    /**
     * 酒店id
     */
    private Integer businessId;
    /**
     * 订单来源
     */
    private String source;
    /**
     * 订单推送时间
     */
    private String createtime;
    /**
     * 订单状态
     */
    private String orderResult;

    /**
     * 微信openid
     */
    private String openid;


    /**
     *开始时间
     */
    private String startDate;


    /**
     * 结束时间
     */
    private String endDate;
}
