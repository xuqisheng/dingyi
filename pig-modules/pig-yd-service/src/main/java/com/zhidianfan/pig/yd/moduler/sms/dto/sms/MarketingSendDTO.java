package com.zhidianfan.pig.yd.moduler.sms.dto.sms;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * @Author zhoubeibei
 * @Description 发送营销短信接收参数
 * @Date Create in 2018/10/30
 * @Modified By:
 */
@Data
public class MarketingSendDTO {
    /**
     * 酒店id
     */
    @Min(1)
    @NumberFormat
    @NotNull
    private Integer businessId;
    /**
     * 模板id
     */
    private Integer templateId;
    /**
     * 短信内容，为空通过模板id获取内容
     */
    private String content;
    /**
     * 客户价值id
     */
    private String vipValueId;
    /**
     * 发送类型
     */
    private Integer sendType;
    /**
     * 发送时间
     */
    private String timer;
    /**
     * 自定义群发内容
     */
    private String custom;
}
