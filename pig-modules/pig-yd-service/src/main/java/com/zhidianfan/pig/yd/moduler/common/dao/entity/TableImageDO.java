package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author wangyz
 * @version v 0.1 2019-05-05 17:08 wangyz Exp $
 */
@Data
public class TableImageDO {

    private Integer id;

    /**
     * 桌位类型 1:方桌 2:卡座 3:圆桌
     */
    private Integer tableType;

    /**
     * 顶部百分比位置
     */
    private String topCoordinate;

    /**
     * 底部百分比位置
     */
    private String bottomCoordinate;

    /**
     * 左侧百分比位置
     */
    private String leftCoordinate;

    /**
     * 右侧百分比位置
     */
    private String rightCoordinate;

    /**
     * 桌位图片类型 目前统一正方形
     */
    private Integer tableImageType;

    /**
     * 关联桌位区域id
     */
    private Integer tableAreaId;

    /**
     * 关联桌位id
     */
    private Integer tableId;

    private Integer businessId;

    private String tableName;

    private String createPerson;

    private String updatePerson;

    private Date createTime;

    private Date updateTime;
}
