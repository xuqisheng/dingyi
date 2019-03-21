package com.zhidianfan.pig.yd.moduler.meituan.dto;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/9/28
 * @Modified By:
 */
@Data
public class TablesOccDTO {

    /**
     * 开发者id
     */
    @NotNull
    private Integer developerId;
    /**
     * 酒店列表
     */
    @NotBlank
    private String ePoiIds;
    /**
     * sign
     */
    @NotBlank
    private String sign;
    /**
     * 开始结束时间
     */
    private String data;

    }
