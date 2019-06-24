package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author wangyz
 * @version v 0.1 2019-05-05 17:08 wangyz Exp $
 */
@Data
public class TableAreaImageDO {

    /**
     * id
     */
    private Integer id;

    /**
     * 是否启用 1:启用  0:禁用
     */
    private Integer status;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 酒店id
     */
    private Integer businessId;

    /**
     * 桌位区域id
     */
    private Integer tableAreaId;

    private String createPerson;

    private String updatePerson;

    private Date createTime;

    private Date updateTime;
}
