package com.zhidianfan.pig.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/23
 * @Modified By:
 */
@Data
public class PushExchangeTable {
    /**
     * 用户名
     */
    private String username;
    /**
     * 消息产生的顺序
     */
    private String msgSeq;
    /**
     * 酒店ID
     */
    private Integer businessId;
    /**
     * 消息
     */
    private Msg msg;

    @Data
    public static class Msg {
        /**
         * 顾客姓名
         */
        private String name;
        /**
         * 手机号
         */
        private String cellPhone;
        /**
         * 换桌前
         */
        private String from;
        /**
         * 换桌后
         */
        private String to;
        /**
         * 换桌后的就餐日期
         */
        @JsonProperty(value = "trans_time'")
        private String transTime;
        /**
         * 餐别
         */
        @JsonProperty(value = "meal_type")
        private String mealType;
        /**
         * 操作员
         */
        private String operator;

        /**
         * 操作员手机号
         */
        private String operPhone;
    }
}
