package com.zhidianfan.pig.yd.moduler.resv.dto;

import com.zhidianfan.pig.yd.moduler.common.dao.entity.SmsMarketing;

/**
 * @Author 李凌峰
 * @Description
 * @Date 2019/1/22 0022 下午 11:39
 * @Modified By:
 */
public class SmsMarkingDTO extends SmsMarketing {

    private Integer appUserId;

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }
}
