package com.zhidianfan.pig.user.dao.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author qqx
 * @since 2018-11-08
 */
@TableName("business")
public class Business extends Model<Business> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 酒店名称
     */
    @TableField("business_name")
    private String businessName;
    /**
     * 上级id
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 上级名称
     */
    @TableField("parent_name")
    private String parentName;
    /**
     * 酒店手机号
     */
    @TableField("business_phone")
    private String businessPhone;
    /**
     * 酒店地址
     */
    @TableField("business_address")
    private String businessAddress;
    /**
     * 酒店星级
     */
    @TableField("business_star")
    private Integer businessStar;
    /**
     * 酒店管理者
     */
    @TableField("business_manager")
    private String businessManager;
    /**
     * wxopenid
     */
    @TableField("open_id")
    private String openId;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 设备列表
     */
    @TableField("device_items")
    private String deviceItems;
    /**
     * 酒店配置列表
     */
    @TableField("business_config_items")
    private String businessConfigItems;
    /**
     * 串口号
     */
    @TableField("serial_port")
    private String serialPort;
    /**
     * 经销商id
     */
    @TableField("agent_id")
    private Integer agentId;
    /**
     * 经销商名称
     */
    @TableField("agent_name")
    private String agentName;
    /**
     * 城市id
     */
    @TableField("city_id")
    private Integer cityId;
    /**
     * 省份id
     */
    @TableField("province_id")
    private Integer provinceId;
    /**
     * 区域id
     */
    @TableField("area_id")
    private Integer areaId;
    @TableField("province_name")
    private String provinceName;
    @TableField("city_name")
    private String cityName;
    @TableField("area_name")
    private String areaName;
    /**
     * 酒店状态 1启用 0停用
     */
    private String status;
    /**
     * 新增时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 地址url
     */
    @TableField("local_url")
    private String localUrl;
    @TableField("licence_url")
    private String licenceUrl;
    /**
     * 版本
     */
    @TableField("soft_version")
    private String softVersion;
    /**
     * 酒店开户时间
     */
    @TableField("open_date")
    private Date openDate;
    /**
     * 结算价格
     */
    private BigDecimal price;
    /**
     * 年费
     */
    @TableField("year_price")
    private BigDecimal yearPrice;
    @TableField("year_price_one")
    private BigDecimal yearPriceOne;
    @TableField("year_price_two")
    private BigDecimal yearPriceTwo;
    @TableField("year_price_three")
    private BigDecimal yearPriceThree;
    /**
     * 到期时间
     */
    @TableField("due_date")
    private Date dueDate;
    @TableField("delay_days")
    private Integer delayDays;
    /**
     * 审批人
     */
    @TableField("review_time")
    private Date reviewTime;
    /**
     * 审批时间
     */
    @TableField("review_user")
    private String reviewUser;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * logo图
     */
    @TableField("logo_pic")
    private String logoPic;

    /**
     * 标签
     */
    private String tag;


    /**
     * 订单免审核开关 0关 1开
     */
    @TableField("order_exemption_verify_check")
    private Integer orderExemptionVerifyCheck;

    /**
     * 默认就餐人数
     */
    @TableField("def_meals_num")
    private Integer defMealsNum;


    /**
     * 桌位标签
     */
    @TableField("table_tag")
    private String tableTag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public Integer getBusinessStar() {
        return businessStar;
    }

    public void setBusinessStar(Integer businessStar) {
        this.businessStar = businessStar;
    }

    public String getBusinessManager() {
        return businessManager;
    }

    public void setBusinessManager(String businessManager) {
        this.businessManager = businessManager;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceItems() {
        return deviceItems;
    }

    public void setDeviceItems(String deviceItems) {
        this.deviceItems = deviceItems;
    }

    public String getBusinessConfigItems() {
        return businessConfigItems;
    }

    public void setBusinessConfigItems(String businessConfigItems) {
        this.businessConfigItems = businessConfigItems;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getLicenceUrl() {
        return licenceUrl;
    }

    public void setLicenceUrl(String licenceUrl) {
        this.licenceUrl = licenceUrl;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getYearPrice() {
        return yearPrice;
    }

    public void setYearPrice(BigDecimal yearPrice) {
        this.yearPrice = yearPrice;
    }

    public BigDecimal getYearPriceOne() {
        return yearPriceOne;
    }

    public void setYearPriceOne(BigDecimal yearPriceOne) {
        this.yearPriceOne = yearPriceOne;
    }

    public BigDecimal getYearPriceTwo() {
        return yearPriceTwo;
    }

    public void setYearPriceTwo(BigDecimal yearPriceTwo) {
        this.yearPriceTwo = yearPriceTwo;
    }

    public BigDecimal getYearPriceThree() {
        return yearPriceThree;
    }

    public void setYearPriceThree(BigDecimal yearPriceThree) {
        this.yearPriceThree = yearPriceThree;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Integer delayDays) {
        this.delayDays = delayDays;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getOrderExemptionVerifyCheck() {
        return orderExemptionVerifyCheck;
    }

    public void setOrderExemptionVerifyCheck(Integer orderExemptionVerifyCheck) {
        this.orderExemptionVerifyCheck = orderExemptionVerifyCheck;
    }

    public Integer getDefMealsNum() {
        return defMealsNum;
    }

    public void setDefMealsNum(Integer defMealsNum) {
        this.defMealsNum = defMealsNum;
    }

    public String getTableTag() {
        return tableTag;
    }

    public void setTableTag(String tableTag) {
        this.tableTag = tableTag;
    }

    @Override
    public String toString() {
        return "Business{" +
        "id=" + id +
        ", businessName=" + businessName +
        ", parentId=" + parentId +
        ", parentName=" + parentName +
        ", businessPhone=" + businessPhone +
        ", businessAddress=" + businessAddress +
        ", businessStar=" + businessStar +
        ", businessManager=" + businessManager +
        ", openId=" + openId +
        ", username=" + username +
        ", password=" + password +
        ", deviceItems=" + deviceItems +
        ", businessConfigItems=" + businessConfigItems +
        ", serialPort=" + serialPort +
        ", agentId=" + agentId +
        ", agentName=" + agentName +
        ", cityId=" + cityId +
        ", provinceId=" + provinceId +
        ", areaId=" + areaId +
        ", provinceName=" + provinceName +
        ", cityName=" + cityName +
        ", areaName=" + areaName +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", localUrl=" + localUrl +
        ", licenceUrl=" + licenceUrl +
        ", softVersion=" + softVersion +
        ", openDate=" + openDate +
        ", price=" + price +
        ", yearPrice=" + yearPrice +
        ", yearPriceOne=" + yearPriceOne +
        ", yearPriceTwo=" + yearPriceTwo +
        ", yearPriceThree=" + yearPriceThree +
        ", dueDate=" + dueDate +
        ", delayDays=" + delayDays +
        ", reviewTime=" + reviewTime +
        ", reviewUser=" + reviewUser +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        ", logo_pic=" + logoPic +
                ", tag=" + tag +
                ", orderExemptionVerifyCheck=" + orderExemptionVerifyCheck +
                ", defMealsNum=" + defMealsNum +
                ", tableTag=" + tableTag +
        "}";
    }
}
