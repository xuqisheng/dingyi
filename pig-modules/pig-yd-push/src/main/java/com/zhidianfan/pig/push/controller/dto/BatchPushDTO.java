package com.zhidianfan.pig.push.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/4
 * @Modified By:
 */
@Data
public class BatchPushDTO {
    /**
     * 酒店id
     */
    @Min(value = 1,message = "酒店id不能为空")
    @NotNull
    private String businessId;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private Set<String> username;
    /**
     * 推送的标题
     */
    private String title;
    /**
     * 推送的内容
     */
    @NotBlank(message = "消息不能为空")
    private String content;
}
