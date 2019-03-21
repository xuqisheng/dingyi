package com.zhidianfan.pig.yd.moduler.resv.dto;

import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/11/14 9:44
 * @DESCRIPTION
 */
@Data
public class ResvTableOrder {

    private String  batchNo;
    private String  eatTime;
    private String  vipName;
    private String  vipPhone;
    private String  tableInfo;
    private String  orderNo;
    private String  status;

    private String createdAt;

}
