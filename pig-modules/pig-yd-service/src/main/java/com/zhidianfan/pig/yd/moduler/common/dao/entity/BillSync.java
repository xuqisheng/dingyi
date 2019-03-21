package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @author sherry
 * @since 2018-08-17
 */
@TableName("bill_sync")
public class BillSync extends Model<BillSync> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_name")
    private String businessName;
    @TableField("area_code")
    private String areaCode;
    @TableField("table_code")
    private String tableCode;
    /**
     * 报表餐别
     */
    private String bbbc;
    /**
     * 账单编号
     */
    private String zdbh;
    /**
     * 实结金额
     */
    private String sjje;
    /**
     * 报表日期
     */
    private Date bbrq;
    @TableField("actual_num")
    private String actualNum;
    @TableField("created_at")
    private Date createdAt;


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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getBbbc() {
        return bbbc;
    }

    public void setBbbc(String bbbc) {
        this.bbbc = bbbc;
    }

    public String getZdbh() {
        return zdbh;
    }

    public void setZdbh(String zdbh) {
        this.zdbh = zdbh;
    }

    public String getSjje() {
        return sjje;
    }

    public void setSjje(String sjje) {
        this.sjje = sjje;
    }

    public Date getBbrq() {
        return bbrq;
    }

    public void setBbrq(Date bbrq) {
        this.bbrq = bbrq;
    }

    public String getActualNum() {
        return actualNum;
    }

    public void setActualNum(String actualNum) {
        this.actualNum = actualNum;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BillSync{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", areaCode=" + areaCode +
        ", tableCode=" + tableCode +
        ", bbbc=" + bbbc +
        ", zdbh=" + zdbh +
        ", sjje=" + sjje +
        ", bbrq=" + bbrq +
        ", actualNum=" + actualNum +
        ", createdAt=" + createdAt +
        "}";
    }
}
