package com.zhidianfan.pig.yd.moduler.sms.bo.business;

import com.zhidianfan.pig.yd.moduler.sms.bo.BaseBO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒店对象
 */
public class BusinessBO extends BaseBO {

    private Long id;

    private String businessName;

    private Integer brandId;

    private String businessPhone;

    private String businessAddress;

    private String businessStar;

    private String businessManager;

    private Integer cityId;

    private Integer provinceId;

    private Integer areaId;

    private String status;

    private String loginUser;

    private String loginPassword;

    private Integer smsStyle;

    private String localUrl;

    private String serialPort;

    private String meetingStatus;

    private Integer devicePhoneType;

    private Integer isShowDevicePhone;

    private Integer isShowTableVipname;

    private Integer isShowPublicVip;

    private Integer isMeetingCheck;

    private Integer changeTableCheck;

    private Integer cancelCheck;

    private Integer changeDishStandardCheck;

    private Integer changeResvTableNumCheck;

    private Integer isOrderQuery;

    private Integer isKbc;

    private String softTitle;

    private Integer isFcb;

    private Date openDate;

    private BigDecimal price;

    private BigDecimal yearPrice;

    private BigDecimal yearPriceOne;

    private BigDecimal yearPriceTwo;

    private BigDecimal yearPriceThree;

    private Date dueDate;

    private Integer delayDays;

    private String tag;

    private String merchantPid;

    private String shopId;

    private Integer isExcelVipPhone;

    private Integer isExcelCheck;

    private Integer isSeatUpdate;

    private Integer notifyBeforeDay;

    private Integer isWeixin;

    private Integer meetingConfirmMessage;

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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    public String getBusinessStar() {
        return businessStar;
    }

    public void setBusinessStar(String businessStar) {
        this.businessStar = businessStar;
    }

    public String getBusinessManager() {
        return businessManager;
    }

    public void setBusinessManager(String businessManager) {
        this.businessManager = businessManager;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Integer getSmsStyle() {
        return smsStyle;
    }

    public void setSmsStyle(Integer smsStyle) {
        this.smsStyle = smsStyle;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public String getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public Integer getDevicePhoneType() {
        return devicePhoneType;
    }

    public void setDevicePhoneType(Integer devicePhoneType) {
        this.devicePhoneType = devicePhoneType;
    }

    public Integer getIsShowDevicePhone() {
        return isShowDevicePhone;
    }

    public void setIsShowDevicePhone(Integer isShowDevicePhone) {
        this.isShowDevicePhone = isShowDevicePhone;
    }

    public Integer getIsShowTableVipname() {
        return isShowTableVipname;
    }

    public void setIsShowTableVipname(Integer isShowTableVipname) {
        this.isShowTableVipname = isShowTableVipname;
    }

    public Integer getIsShowPublicVip() {
        return isShowPublicVip;
    }

    public void setIsShowPublicVip(Integer isShowPublicVip) {
        this.isShowPublicVip = isShowPublicVip;
    }

    public Integer getIsMeetingCheck() {
        return isMeetingCheck;
    }

    public void setIsMeetingCheck(Integer isMeetingCheck) {
        this.isMeetingCheck = isMeetingCheck;
    }

    public Integer getChangeTableCheck() {
        return changeTableCheck;
    }

    public void setChangeTableCheck(Integer changeTableCheck) {
        this.changeTableCheck = changeTableCheck;
    }

    public Integer getCancelCheck() {
        return cancelCheck;
    }

    public void setCancelCheck(Integer cancelCheck) {
        this.cancelCheck = cancelCheck;
    }

    public Integer getChangeDishStandardCheck() {
        return changeDishStandardCheck;
    }

    public void setChangeDishStandardCheck(Integer changeDishStandardCheck) {
        this.changeDishStandardCheck = changeDishStandardCheck;
    }

    public Integer getChangeResvTableNumCheck() {
        return changeResvTableNumCheck;
    }

    public void setChangeResvTableNumCheck(Integer changeResvTableNumCheck) {
        this.changeResvTableNumCheck = changeResvTableNumCheck;
    }

    public Integer getIsOrderQuery() {
        return isOrderQuery;
    }

    public void setIsOrderQuery(Integer isOrderQuery) {
        this.isOrderQuery = isOrderQuery;
    }

    public Integer getIsKbc() {
        return isKbc;
    }

    public void setIsKbc(Integer isKbc) {
        this.isKbc = isKbc;
    }

    public String getSoftTitle() {
        return softTitle;
    }

    public void setSoftTitle(String softTitle) {
        this.softTitle = softTitle;
    }

    public Integer getIsFcb() {
        return isFcb;
    }

    public void setIsFcb(Integer isFcb) {
        this.isFcb = isFcb;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMerchantPid() {
        return merchantPid;
    }

    public void setMerchantPid(String merchantPid) {
        this.merchantPid = merchantPid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getIsExcelVipPhone() {
        return isExcelVipPhone;
    }

    public void setIsExcelVipPhone(Integer isExcelVipPhone) {
        this.isExcelVipPhone = isExcelVipPhone;
    }

    public Integer getIsExcelCheck() {
        return isExcelCheck;
    }

    public void setIsExcelCheck(Integer isExcelCheck) {
        this.isExcelCheck = isExcelCheck;
    }

    public Integer getIsSeatUpdate() {
        return isSeatUpdate;
    }

    public void setIsSeatUpdate(Integer isSeatUpdate) {
        this.isSeatUpdate = isSeatUpdate;
    }

    public Integer getNotifyBeforeDay() {
        return notifyBeforeDay;
    }

    public void setNotifyBeforeDay(Integer notifyBeforeDay) {
        this.notifyBeforeDay = notifyBeforeDay;
    }

    public Integer getIsWeixin() {
        return isWeixin;
    }

    public void setIsWeixin(Integer isWeixin) {
        this.isWeixin = isWeixin;
    }

    public Integer getMeetingConfirmMessage() {
        return meetingConfirmMessage;
    }

    public void setMeetingConfirmMessage(Integer meetingConfirmMessage) {
        this.meetingConfirmMessage = meetingConfirmMessage;
    }
}
