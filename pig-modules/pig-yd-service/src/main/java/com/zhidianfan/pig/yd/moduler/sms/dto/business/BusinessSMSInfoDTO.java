package com.zhidianfan.pig.yd.moduler.sms.dto.business;

import com.zhidianfan.pig.yd.moduler.sms.dto.BaseDTO;

/**
 *
 * Created by Administrator on 2017/12/18.
 */
public class BusinessSMSInfoDTO extends BaseDTO {


    /* business_sms_role 内容 */
    /**
     * 酒店id
     */
    private Long businessId;

    /**
     * 订单类型
     */
    private Integer resvOrderTypeId;

    /**
     * 预定是否发送
     */
    private Integer order;

    /**
     * 入座是否发送
     */
    private Integer checkIn;

    /**
     * 换坐是否发送
     */
    private Integer change;

    /**
     * 取消是否发送
     */
    private Integer cancel;

    /**
     * 确认是否发送
     */
    private Integer confirm;

    /**
     * 加桌是否发送
     */
    private Integer addTable;

    /**
     * 预定补发是否发送
     */
    private Integer mend;

    /* business_sms_setting 内容 */
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

    /* business_sms 内容 */
    /**
     * 酒店自定义消息
     */
    private String smsMessage;

    /**
     * 短信阈值
     */
    private Integer minSmsNum;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Integer getResvOrderTypeId() {
        return resvOrderTypeId;
    }

    public void setResvOrderTypeId(Integer resvOrderTypeId) {
        this.resvOrderTypeId = resvOrderTypeId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Integer checkIn) {
        this.checkIn = checkIn;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
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

    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }

    public Integer getAddTable() {
        return addTable;
    }

    public void setAddTable(Integer addTable) {
        this.addTable = addTable;
    }

    public Integer getMend() {
        return mend;
    }

    public void setMend(Integer mend) {
        this.mend = mend;
    }

    public Integer getMinSmsNum() {
        return minSmsNum;
    }

    public void setMinSmsNum(Integer minSmsNum) {
        this.minSmsNum = minSmsNum;
    }
}
