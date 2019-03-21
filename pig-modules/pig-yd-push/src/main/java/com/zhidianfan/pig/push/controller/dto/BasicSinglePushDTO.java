package com.zhidianfan.pig.push.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/5
 * @Modified By:
 */
@Data
public class BasicSinglePushDTO {
    @NotBlank(message = "设备id不能为空")
    private String registrationId;
    @NotBlank(message = "推送内容不能为空")
    private String content;
}
