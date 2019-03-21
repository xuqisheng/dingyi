package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import java.util.Date;
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
 * @since 2019-03-12
 */
@TableName("hotel_staff_mapping")
public class HotelStaffMapping extends Model<HotelStaffMapping> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库自增id
     */
    private Long id;
    /**
     * 酒店id
     */
    @TableField("hotel_id")
    private Long hotelId;
    /**
     * 酒店名称
     */
    @TableField("hotel_name")
    private String hotelName;
    /**
     * 业务id
     */
    @TableField("staff_id")
    private String staffId;
    /**
     * 状态 1:启用  0:禁用
     */
    private Integer status;
    /**
     * 类型 1:小程序  2:门店后台
     */
    private Integer type;
    /**
     * 关联APP_type表
     */
    @TableField("app_type_id")
    private Long appTypeId;
    /**
     * 员工编码
     */
    @TableField("staff_code")
    private String staffCode;
    /**
     * 操作区域id
     */
    @TableField("operation_area_id")
    private String operationAreaId;
    /**
     * 标签
     */
    private String tag;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 更新人
     */
    @TableField("update_user")
    private String updateUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(Long appTypeId) {
        this.appTypeId = appTypeId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getOperationAreaId() {
        return operationAreaId;
    }

    public void setOperationAreaId(String operationAreaId) {
        this.operationAreaId = operationAreaId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "HotelStaffMapping{" +
        "id=" + id +
        ", hotelId=" + hotelId +
        ", hotelName=" + hotelName +
        ", staffId=" + staffId +
        ", status=" + status +
        ", appTypeId=" + appTypeId +
        ", staffCode=" + staffCode +
        ", operationAreaId=" + operationAreaId +
        ", tag=" + tag +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
