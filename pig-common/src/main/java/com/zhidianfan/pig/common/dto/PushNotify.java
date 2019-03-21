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
public class PushNotify {
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
        private Integer count;
    }
}
