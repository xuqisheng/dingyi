package com.zhidianfan.pig.yd.moduler.dbo.business;

import com.zhidianfan.pig.yd.moduler.dbo.BaseDO;

/**
 * 酒店短信模板配置
 * Created by Administrator on 2017/12/18.
 */
public class BusinessSMSSettingsDO extends BaseDO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 短信类型
     */
    private Integer type;

    /**
     * 发送短信是否显示客户姓名
     */
    private Integer vipNameType;

    /**
     * 发送短息是否显示区域
     */
    private Integer areaNameType;

    /**
     * 发送短信是否显示桌位
     */
    private Integer tableNameType;

    /**
     * 发送短信是否显示预定人数
     */
    private Integer resvNumType;

    /**
     * 发送短信是否发送地址
     */
    private Integer addressType;

    /**
     * 发送短信是否显示星期
     */
    private Integer weekNameType;

    /**
     * 发送短信是否显示导航
     */
    private Integer mapType;

    /**
     * 发送短信是否显示酒店号码
     */
    private Integer phoneType;

    /**
     * 发送短信是否显示营销经理
     */
    private Integer appUserType;

    /**
     * 发送短信是否显示押金金额
     */
    private Integer depositType;

    /**
     * 发送短信是否显示押金支付方式
     */
    private Integer payTypeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVipNameType() {
        return vipNameType;
    }

    public void setVipNameType(Integer vipNameType) {
        this.vipNameType = vipNameType;
    }

    public Integer getAreaNameType() {
        return areaNameType;
    }

    public void setAreaNameType(Integer areaNameType) {
        this.areaNameType = areaNameType;
    }

    public Integer getTableNameType() {
        return tableNameType;
    }

    public void setTableNameType(Integer tableNameType) {
        this.tableNameType = tableNameType;
    }

    public Integer getResvNumType() {
        return resvNumType;
    }

    public void setResvNumType(Integer resvNumType) {
        this.resvNumType = resvNumType;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public Integer getWeekNameType() {
        return weekNameType;
    }

    public void setWeekNameType(Integer weekNameType) {
        this.weekNameType = weekNameType;
    }

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }

    public Integer getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(Integer phoneType) {
        this.phoneType = phoneType;
    }

    public Integer getAppUserType() {
        return appUserType;
    }

    public void setAppUserType(Integer appUserType) {
        this.appUserType = appUserType;
    }

    public Integer getDepositType() {
        return depositType;
    }

    public void setDepositType(Integer depositType) {
        this.depositType = depositType;
    }

    public Integer getPayTypeType() {
        return payTypeType;
    }

    public void setPayTypeType(Integer payTypeType) {
        this.payTypeType = payTypeType;
    }
}
