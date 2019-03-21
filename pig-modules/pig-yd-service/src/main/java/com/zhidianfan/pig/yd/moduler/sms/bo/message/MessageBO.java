package com.zhidianfan.pig.yd.moduler.sms.bo.message;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * message发送对象
 */
public class MessageBO implements Serializable {

    //酒店id
    private Long businessId;

    //短信模板
    private Integer templateType;

    //预定类型
    private Integer resvOrderTypeId;

    //电话号码，多个用，隔开
    private String phone;

    //订单号
    private String resvOrder;

    //桌位名称 用于预定和入座消息
    private List<Map<String, Object>> checkedTables;

    //旧桌
    private String oldTableName;

    //旧区域
    private String oldTableAreaName;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Integer getResvOrderTypeId() {
        return resvOrderTypeId;
    }

    public void setResvOrderTypeId(Integer resvOrderTypeId) {
        this.resvOrderTypeId = resvOrderTypeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResvOrder() {
        return resvOrder;
    }

    public void setResvOrder(String resvOrder) {
        this.resvOrder = resvOrder;
    }

    public List<Map<String, Object>> getCheckedTables() {
        return checkedTables;
    }

    public void setCheckedTables(List<Map<String, Object>> checkedTables) {
        this.checkedTables = checkedTables;
    }

    public String getOldTableName() {
        return oldTableName;
    }

    public void setOldTableName(String oldTableName) {
        this.oldTableName = oldTableName;
    }

    public String getOldTableAreaName() {
        return oldTableAreaName;
    }

    public void setOldTableAreaName(String oldTableAreaName) {
        this.oldTableAreaName = oldTableAreaName;
    }
}
