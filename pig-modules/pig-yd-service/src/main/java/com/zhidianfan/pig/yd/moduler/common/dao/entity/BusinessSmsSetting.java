package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Conan
 * @since 2018-09-10
 */
@TableName("business_sms_setting")
public class BusinessSmsSetting extends Model<BusinessSmsSetting> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 短信类型 1-预订, 2-换桌, 3-退订
     */
    private Integer type;
    @TableField("vip_name_type")
    private Integer vipNameType;
    @TableField("table_name_type")
    private Integer tableNameType;
    @TableField("area_name_type")
    private Integer areaNameType;
    @TableField("week_name_type")
    private Integer weekNameType;
    @TableField("map_type")
    private Integer mapType;
    @TableField("phone_type")
    private Integer phoneType;
    @TableField("app_user_type")
    private Integer appUserType;
    @TableField("address_type")
    private Integer addressType;
    @TableField("resv_num_type")
    private Integer resvNumType;
    /**
     * 押金是否显示
     */
    @TableField("deposit_type")
    private Integer depositType;
    /**
     * 支付类型是否显示
     */
    @TableField("pay_type_type")
    private Integer payTypeType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
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

    public Integer getTableNameType() {
        return tableNameType;
    }

    public void setTableNameType(Integer tableNameType) {
        this.tableNameType = tableNameType;
    }

    public Integer getAreaNameType() {
        return areaNameType;
    }

    public void setAreaNameType(Integer areaNameType) {
        this.areaNameType = areaNameType;
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

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public Integer getResvNumType() {
        return resvNumType;
    }

    public void setResvNumType(Integer resvNumType) {
        this.resvNumType = resvNumType;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessSmsSetting{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", type=" + type +
        ", vipNameType=" + vipNameType +
        ", tableNameType=" + tableNameType +
        ", areaNameType=" + areaNameType +
        ", weekNameType=" + weekNameType +
        ", mapType=" + mapType +
        ", phoneType=" + phoneType +
        ", appUserType=" + appUserType +
        ", addressType=" + addressType +
        ", resvNumType=" + resvNumType +
        ", depositType=" + depositType +
        ", payTypeType=" + payTypeType +
        "}";
    }
}
