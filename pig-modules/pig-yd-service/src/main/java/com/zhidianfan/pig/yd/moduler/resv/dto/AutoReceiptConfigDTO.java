package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * @author huzp
 * @date 2019/1/28 0028 14:59
 * @description
 */
public class AutoReceiptConfigDTO  {

    private Integer id;
    /**
     * 酒店id
     */
    private Integer businessId;
    /**
     * 酒店名称
     */
    private String businessName;
    /**
     * 自动接单状态 0:不启用 1:启用
     */
    private Integer status;
    /**
     * 保留桌位数量
     */
    private Integer reservedTableCount;
    /**
     * 不自动接单的桌位id
     */
    private String reservedTableIds;


}
