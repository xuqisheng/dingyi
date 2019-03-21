package com.zhidianfan.pig.yd.moduler.resv.dto;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author: huzp
 * @Date: 2018/9/26 11:08
 */
@Data
public class CustomerDTO {

    /**
     * 酒店id
     */
    @NotNull(message = "酒店id不能为空")
    private Integer businessId;
    /**
     * 酒店名字
     */
    @NotBlank(message = "酒店名称不能为空")
    private String businessName;
    /**
     * 客户电话
     */
    @NotBlank(message = "客户电话不能为空")
    private String vipPhone;
    /**
     * 客户姓名
     */
    private String vipName;
    /**
     * 客户性别
     */
    private String vipSex;

    /**
     * 客户性别
     */
    private Integer operation;



}
