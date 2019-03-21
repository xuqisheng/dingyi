package com.zhidianfan.pig.yd.moduler.meituan.dto;

import io.swagger.models.auth.In;

import java.util.Date;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/16
 * @Modified By:
 */
public class KbOrderDTO {

    /**
     * resvDate : 2018-11-16 15:30
     * vipName : 1
     * resvNum : 1
     * vipPhone : 13777575146
     * mealTypeId : 9
     * mealTypeName : 午餐
     * vipSex : 男
     * businessName : 鼎壹大酒店
     * businessId : 30
     * remark :
     * createTime : 2018-11-16 14:20
     */

    private Date resvDate;
    private String vipName;
    private Integer resvNum;
    private String vipPhone;
    private String vipSex;
    private String businessName;
    private Integer businessId;
    private String remark;
    private Integer tableType;
    private String tableTypeName;

    public Date getResvDate() {
        return resvDate;
    }

    public void setResvDate(Date resvDate) {
        this.resvDate = resvDate;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public Integer getResvNum() {
        return resvNum;
    }

    public void setResvNum(Integer resvNum) {
        this.resvNum = resvNum;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTableType() {
        return tableType;
    }

    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }

    public String getTableTypeName() {
        return tableTypeName;
    }

    public void setTableTypeName(String tableTypeName) {
        this.tableTypeName = tableTypeName;
    }

}
