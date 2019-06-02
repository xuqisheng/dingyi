package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sjl
 * 2019-05-29 09:35
 */
@Data
public class CustomerValueChangeFieldDTO implements Serializable {

    /**
     * 资料完善度
     */
    public static final String PROFILE = "PROFILE";
    /**
     * 首次消费时间
     */
    public static final String FIRST_CUSTOMER_TIME = "FIRST_CUSTOMER_TIME";
    /**
     * 撤单桌数
     */
    public static final String CANCEL_ORDER_TABLE = "CANCEL_ORDER_TABLE";

    private Integer vipId;
    /**
     * 类型,profile-资料完善度
     */
    private String type;
    private String value;
}
