package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 桌位订单类
 * @author LJH
 * @version 1.0
 * @Date 2018/10/12 09:52
 */
@Data
public class DeskOrderBo extends ResvOrderAndroid {

    /**
     * 价值名称
     */
    private String vipValueName;

    /**
     * 客户类型
     */
    private String vipClassName;


    /**
     * 批次订单需桌位
     */
    private List tables;


    /**
     * 批次订单总人数
     */
    private Integer headcount;


    /**
     * 超时等级 0超时 1严重超时
     */
    private Integer timeoutLevel;


    @ApiModelProperty("订单评价id")
    private Integer orderRatingId;

}
