package com.zhidianfan.pig.yd.moduler.dbo.business;

import com.zhidianfan.pig.yd.moduler.dbo.BaseDO;

/**
 * 酒店预订类型发送配置
 * Created by Administrator on 2017/12/18.
 */
public class BusinessSMSRoleDO extends BaseDO {

    /**
     * 自增id
     */
    private Long id;

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
     * 加桌短信
     */
    private Integer addTable;

    /**
     * 预定补发
     */
    private Integer mend;

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
}
