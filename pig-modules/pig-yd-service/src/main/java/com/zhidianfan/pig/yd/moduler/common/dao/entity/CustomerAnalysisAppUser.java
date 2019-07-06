package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import lombok.Data;

/**
 * @author wangyz
 * @version v 0.1 2019-04-16 16:12 wangyz Exp $
 */
@Data
public class CustomerAnalysisAppUser {
    private String date;
    private String appUserName;
    private Integer appUserId;
    private Integer businessId;
    private Integer newVipNum;
    private Integer awakenVipNum;
}
