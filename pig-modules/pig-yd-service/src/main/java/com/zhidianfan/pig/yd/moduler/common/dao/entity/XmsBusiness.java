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
 * @author sherry
 * @since 2018-08-16
 */
@TableName("xms_business")
public class XmsBusiness extends Model<XmsBusiness> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 集团酒店id
     */
    @TableField("brand_hotel_id")
    private String brandHotelId;
    /**
     * 业务酒店id
     */
    @TableField("business_hotel_id")
    private String businessHotelId;
    /**
     * 西软营业点用,分割
     */
    @TableField("pccodes")
    private String pccodes;


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

    public String getBrandHotelId() {
        return brandHotelId;
    }

    public void setBrandHotelId(String brandHotelId) {
        this.brandHotelId = brandHotelId;
    }

    public String getBusinessHotelId() {
        return businessHotelId;
    }

    public void setBusinessHotelId(String businessHotelId) {
        this.businessHotelId = businessHotelId;
    }

    public String getPccodes() {
        return pccodes;
    }

    public void setPccodes(String pccodes) {
        this.pccodes = pccodes;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "XmsBusiness{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", brandHotelId=" + brandHotelId +
        ", businessHotelId=" + businessHotelId +
        "}";
    }
}
