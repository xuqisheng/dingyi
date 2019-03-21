package com.zhidianfan.pig.yd.moduler.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author mikrotik
 */
@Getter
@Setter
@ToString
public class InfoDTO {
    @NotNull
    private String businessId;
    private String beginTime;
    private String endTime;
    private String pcCode;
}
