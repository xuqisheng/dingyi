package com.zhidianfan.pig.yd.moduler.sms.dto.sms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-13
 * @Time: 17:03
 */
@Data
public class NotificationTemplateDTO {


    private Integer id;

    private Integer businessId;

    private String templateContent;

    /**
     * 类型  1-预订,2-确认, 3-入座,4-换桌, 5-退订
     */
    @ApiModelProperty("1-预订,2-确认, 3-入座,4-换桌, 5-退订 ，6-加桌")
    private Integer templateType;

    private Integer status;
}
