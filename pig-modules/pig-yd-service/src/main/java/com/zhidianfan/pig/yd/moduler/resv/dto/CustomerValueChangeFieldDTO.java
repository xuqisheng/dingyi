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

    /**
     * 当前营销经理 id
     */
    public static final String CURRENT_APP_USER_ID = "CURRENT_APP_USER_ID";

    /**
     * 当前营销经理名称
     */
    public static final String CURRENT_APP_USER_NAME = "CURRENT_APP_USER_NAME";

    /**
     * 当前营销经理电话
     */
    public static final String CURRENT_APP_USER_PHONE = "CURRENT_APP_USER_PHONE";

    private Integer vipId;
    /**
     * 类型,profile-资料完善度
     */
    private String type;
    private String value;
}
