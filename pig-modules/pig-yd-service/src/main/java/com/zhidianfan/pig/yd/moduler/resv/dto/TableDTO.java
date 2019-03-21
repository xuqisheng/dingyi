package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: huzp
 * @Date: 2018/9/27 16:07
 */
@Data
public class TableDTO {

    /**
     * 区域id
     */
    @NotEmpty(message = "区域ID不存在")
    private Integer tableAreaId;
    /**
     * 区域名称
     */
    @NotBlank(message = "区域名字不存在")
    private String tableAreaName;
    /**
     * 桌位id
     */
    @NotEmpty(message = "桌位ID不存在")
    private Integer tableId;
    /**
     * 桌位名称
     */
    @NotBlank(message = "桌位名称不存在")
    private String tableName;
    /**
     * 桌位容纳最大人数
     */
    private String maxPeopleNum;

    /**
     * 预订人数
     */
    private String resvNum;
}
