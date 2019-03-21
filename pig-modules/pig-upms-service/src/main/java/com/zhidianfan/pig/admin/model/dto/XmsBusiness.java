package com.zhidianfan.pig.admin.model.dto;

/**
 * <p>
 * 
 * </p>
 *
 * @author sherry
 * @since 2018-08-16
 */
public class XmsBusiness {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
    private Integer id;
    /**
     * 酒店id
     */
    private Integer businessId;
    /**
     * 集团酒店id
     */
    private String brandHotelId;
    /**
     * 业务酒店id
     */
    private String businessHotelId;


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

    @Override
    public String toString() {
        return "XmsBusiness{" +
        ", id=" + id +
        ", businessId=" + businessId +
        ", brandHotelId=" + brandHotelId +
        ", businessHotelId=" + businessHotelId +
        "}";
    }
}
