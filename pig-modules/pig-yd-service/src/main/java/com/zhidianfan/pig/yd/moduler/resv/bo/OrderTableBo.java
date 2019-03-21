package com.zhidianfan.pig.yd.moduler.resv.bo;

import lombok.Data;

/**
 * @author LJH
 * @version 1.0
 * @Date 2018/10/24 10:02
 */
@Data
public class OrderTableBo {

    /**
     * 桌位id
     */
    private Integer tableId;

    /**
     * 桌位人数
     */
    private Integer resvNum;
}
