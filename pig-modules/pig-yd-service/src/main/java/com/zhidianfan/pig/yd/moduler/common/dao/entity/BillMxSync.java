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
@TableName("bill_mx_sync")
public class BillMxSync extends Model<BillMxSync> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_name")
    private String businessName;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 账单编号
     */
    private String zdbh;
    /**
     * 菜名编号
     */
    private String cmbh;
    /**
     * 菜名名称
     */
    private String cmmc;
    /**
     * 实结金额
     */
    private Double sjje;
    /**
     * 备注
     */
    private String wdbz;
    /**
     * 菜名数量
     */
    private Integer cmsl;
    @TableField("created_at")
    private Date createdAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getZdbh() {
        return zdbh;
    }

    public void setZdbh(String zdbh) {
        this.zdbh = zdbh;
    }

    public String getCmbh() {
        return cmbh;
    }

    public void setCmbh(String cmbh) {
        this.cmbh = cmbh;
    }

    public String getCmmc() {
        return cmmc;
    }

    public void setCmmc(String cmmc) {
        this.cmmc = cmmc;
    }

    public Double getSjje() {
        return sjje;
    }

    public void setSjje(Double sjje) {
        this.sjje = sjje;
    }

    public String getWdbz() {
        return wdbz;
    }

    public void setWdbz(String wdbz) {
        this.wdbz = wdbz;
    }

    public Integer getCmsl() {
        return cmsl;
    }

    public void setCmsl(Integer cmsl) {
        this.cmsl = cmsl;
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
        return "BillMxSync{" +
        "id=" + id +
        ", businessName=" + businessName +
        ", businessId=" + businessId +
        ", zdbh=" + zdbh +
        ", cmbh=" + cmbh +
        ", cmmc=" + cmmc +
        ", sjje=" + sjje +
        ", wdbz=" + wdbz +
        ", cmsl=" + cmsl +
        ", createdAt=" + createdAt +
        "}";
    }
}
