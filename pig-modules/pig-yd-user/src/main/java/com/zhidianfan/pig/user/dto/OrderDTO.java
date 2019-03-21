package com.zhidianfan.pig.user.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-15
 * @Time: 09:54
 */
@Data
public class OrderDTO {

    /**
     * 酒店id
     */
    Integer businessId;
    /**
     * 餐区id
     */
    Integer tableAreaId;
    /**
     * 状态 1 已预订 2已入座 3已结账 4已退订 5锁定 6解锁
     */
    List<Integer> status;


    /**
     * 桌位id
     */
    private Integer tableId;
}
