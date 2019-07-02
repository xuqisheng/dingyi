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
 * @author huzp
 * @since 2019-06-13
 */
@TableName("sms_num_remind")
public class SmsNumRemind extends Model<SmsNumRemind> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    @TableField("businesss_name")
    private String businesssName;
    /**
     * 客户端 : 1. 电话机 2.小程序
     */
    @TableField("client_type")
    private Integer clientType;
    /**
     * 提醒次数为1次
     */
    @TableField("remind_num")
    private Integer remindNum;
    /**
     * 提醒类型: 1. 50条  2.20条
     */
    @TableField("remind_type")
    private Integer remindType;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;
    /**
     * 更新时间
     */
    @TableField("update_at")
    private Date updateAt;


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

    public String getBusinesssName() {
        return businesssName;
    }

    public void setBusinesssName(String businesssName) {
        this.businesssName = businesssName;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getRemindNum() {
        return remindNum;
    }

    public void setRemindNum(Integer remindNum) {
        this.remindNum = remindNum;
    }

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SmsNumRemind{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businesssName=" + businesssName +
        ", clientType=" + clientType +
        ", remindNum=" + remindNum +
        ", remindType=" + remindType +
        ", createAt=" + createAt +
        ", updateAt=" + updateAt +
        "}";
    }
}
