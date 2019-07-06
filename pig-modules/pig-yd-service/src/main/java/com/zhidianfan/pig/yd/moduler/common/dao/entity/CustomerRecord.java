package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 客户记录表
 * </p>
 *
 * @author 
 * @since 2019-05-28
 */
@TableName("customer_record")
public class CustomerRecord extends Model<CustomerRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * vip 表主键
     */
    @TableField("vip_id")
    private Integer vipId;

    /**
     * 日志类型
     */
    @TableField("log_type")
    private Integer logType;
    /**
     * 记录时间
     */
    @TableField("log_time")
    private LocalDateTime logTime;
    /**
     * 关联订单
     */
    @TableField("resv_order")
    private String resvOrder;
    /**
     * 预定日期
     */
    @TableField("resv_date")
    private LocalDateTime resvDate;
    /**
     * 餐别 id
     */
    @TableField("meal_type_id")
    private Integer mealTypeId;
    /**
     * 餐别名称
     */
    @TableField("meal_type_name")
    private String mealTypeName;
    /**
     * 消费金额,单位:分
     */
    @TableField("consume_amount")
    private Integer consumeAmount;
    /**
     * 人数
     */
    @TableField("person_no")
    private Integer personNo;
    /**
     * 桌位 id
     */
    @TableField("table_id")
    private Integer tableId;
    /**
     * 桌位名称
     */
    @TableField("table_name")
    private String tableName;
    /**
     * 预订人
     */
    @TableField("vip_name")
    private String vipName;
    /**
     * 预订人手机
     */
    @TableField("vip_phone")
    private String vipPhone;
    /**
     * 营销经理名称
     */
    @TableField("app_user_id")
    private Integer appUserId;
    /**
     * 营销经理名称
     */
    @TableField("app_user_name")
    private String appUserName;
    /**
     * 营销经理手机号码
     */
    @TableField("app_user_phone")
    private String appUserPhone;
    /**
     * 旧的一级价值
     */
    @TableField("old_first_value")
    private Integer oldFirstValue;
    /**
     * 新的一级价值
     */
    @TableField("new_first_value")
    private Integer newFirstValue;
    /**
     * 操作记录
     */
    @TableField("operation_log")
    private String operationLog;
    /**
     * 创建人 user_id
     */
    @TableField("create_user_id")
    private Long createUserId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新人 user_id
     */
    @TableField("update_user_id")
    private Long updateUserId;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public String getResvOrder() {
        return resvOrder;
    }

    public void setResvOrder(String resvOrder) {
        this.resvOrder = resvOrder;
    }

    public LocalDateTime getResvDate() {
        return resvDate;
    }

    public void setResvDate(LocalDateTime resvDate) {
        this.resvDate = resvDate;
    }

    public Integer getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(Integer mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public Integer getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(Integer consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public Integer getPersonNo() {
        return personNo;
    }

    public void setPersonNo(Integer personNo) {
        this.personNo = personNo;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserPhone() {
        return appUserPhone;
    }

    public void setAppUserPhone(String appUserPhone) {
        this.appUserPhone = appUserPhone;
    }

    public String getOperationLog() {
        return operationLog;
    }

    public void setOperationLog(String operationLog) {
        this.operationLog = operationLog;
    }

    public Integer getOldFirstValue() {
        return oldFirstValue;
    }

    public void setOldFirstValue(Integer oldFirstValue) {
        this.oldFirstValue = oldFirstValue;
    }

    public Integer getNewFirstValue() {
        return newFirstValue;
    }

    public void setNewFirstValue(Integer newFirstValue) {
        this.newFirstValue = newFirstValue;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomerRecord{" +
        "id=" + id +
        ", logType=" + logType +
        ", vipId=" + vipId +
        ", logTime=" + logTime +
        ", resvOrder=" + resvOrder +
        ", resvDate=" + resvDate +
        ", mealTypeId=" + mealTypeId +
        ", mealTypeName=" + mealTypeName +
        ", consumeAmount=" + consumeAmount +
        ", personNo=" + personNo +
        ", tableId=" + tableId +
        ", tableName=" + tableName +
        ", vipName=" + vipName +
        ", vipPhone=" + vipPhone +
        ", appUserName=" + appUserName +
        ", appUserId=" + appUserId +
        ", appUserPhone=" + appUserPhone +
        ", operationLog=" + operationLog +
        ", oldFirstValue=" + oldFirstValue +
        ", operationLog=" + newFirstValue +
        ", newFirstValue=" + createUserId +
        ", createTime=" + createTime +
        ", updateUserId=" + updateUserId +
        ", updateTime=" + updateTime +
        "}";
    }
}
