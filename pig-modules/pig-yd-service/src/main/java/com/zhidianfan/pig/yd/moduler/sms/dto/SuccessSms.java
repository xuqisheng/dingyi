package com.zhidianfan.pig.yd.moduler.sms.dto;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;

import java.util.Map;

/**
 * @Author Conan
 * @Description
 * @Date: 2018/9/19 0019 上午 11:39
 * @Modified By:
 */
public class SuccessSms extends SuccessTip {
    private Map<String, Object> extDate;

    public Map<String, Object> getExtDate() {
        return this.extDate;
    }

    public void setExtDate(Map<String, Object> extDate) {
        this.extDate = extDate;
    }
}
