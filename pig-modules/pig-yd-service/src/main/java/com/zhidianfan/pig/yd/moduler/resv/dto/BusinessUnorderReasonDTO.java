package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-07
 * @Time: 16:13
 */
@Data
public class BusinessUnorderReasonDTO {

    @NotBlank(message = "内容不能为空")
    private String unorderReasonName;

    @NotBlank(message = "酒店id不能为空")
    private String businessId;
}
