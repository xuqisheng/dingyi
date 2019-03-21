package com.zhidianfan.pig.yd.moduler.meituan.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


/**
 * @Author qqx
 * @Description 基础服务DTO
 * @Date Create in 2018/9/06
 * @Modified By:
 */
@Data
public class OrderDTO {

    /**
     * 开发者id
     */
    @NotNull
    private Integer developerId;
    /**
     * 酒店id
     */
    @NotBlank
    private String ePoiId;
    /**
     * sign
     */
    @NotBlank
    private String sign;
    /**
     * 请求数据
     */
    @NotBlank
    private String data;

}
