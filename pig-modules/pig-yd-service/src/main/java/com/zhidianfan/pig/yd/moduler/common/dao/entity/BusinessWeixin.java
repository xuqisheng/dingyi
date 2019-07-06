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
 * @author qqx
 * @since 2019-05-30
 */
@TableName("business_weixin")
public class BusinessWeixin extends Model<BusinessWeixin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    @TableField("business_id_wx")
    private String businessIdWx;
    @TableField("business_name")
    private String businessName;
    @TableField("business_name_wx")
    private String businessNameWx;
    private String jd;
    private String wd;
    private String imgurl;
    private Integer status;
    @TableField("brand_id")
    private Integer brandId;
    @TableField("map_imgurl")
    private String mapImgurl;
    @TableField("xq_imgurl")
    private String xqImgurl;


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

    public String getBusinessIdWx() {
        return businessIdWx;
    }

    public void setBusinessIdWx(String businessIdWx) {
        this.businessIdWx = businessIdWx;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessNameWx() {
        return businessNameWx;
    }

    public void setBusinessNameWx(String businessNameWx) {
        this.businessNameWx = businessNameWx;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getMapImgurl() {
        return mapImgurl;
    }

    public void setMapImgurl(String mapImgurl) {
        this.mapImgurl = mapImgurl;
    }

    public String getXqImgurl() {
        return xqImgurl;
    }

    public void setXqImgurl(String xqImgurl) {
        this.xqImgurl = xqImgurl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessWeixin{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businessIdWx=" + businessIdWx +
        ", businessName=" + businessName +
        ", businessNameWx=" + businessNameWx +
        ", jd=" + jd +
        ", wd=" + wd +
        ", imgurl=" + imgurl +
        ", status=" + status +
        ", brandId=" + brandId +
        ", mapImgurl=" + mapImgurl +
        ", xqImgurl=" + xqImgurl +
        "}";
    }
}
