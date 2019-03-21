package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LJH
 * @version 1.0
 * @Date 2018/10/26 09:31
 */
@Data
public class ThirdPartyInfoEditDTO {

    @NotNull(message = "酒店id不能为空")
    private Integer businessId;

    private String businessName;

    private  Integer id;

    private String name;
}
