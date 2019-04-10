package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: hzp
 * @Date: 2019-04-10 10:02
 * @Description:
 */
@Data
public class CustomerDateClearDTO {

    /**
     * 0. 纪念日
     * 1. 生日
     */
    @NotNull
    private Integer type;
    /**
     * 1.如果为纪念日则是纪念日id
     * 2.如果是生日则是客户id
     */
    @NotNull
    private Integer calendarId;
}
