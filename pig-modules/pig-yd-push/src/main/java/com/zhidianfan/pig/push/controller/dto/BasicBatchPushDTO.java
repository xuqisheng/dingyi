package com.zhidianfan.pig.push.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/5
 * @Modified By:
 */
@Data
public class BasicBatchPushDTO {
    @NotBlank(message = "设备id不能为空")
    private Set<String> registrationIds;
    @NotBlank(message = "推送内容不能为空")
    private String content;
}
