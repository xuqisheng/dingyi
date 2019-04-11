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
 * @author sherry
 * @since 2018-09-20
 */
@TableName("resv_order_third")
public class ResvOrderThird extends Model<ResvOrderThird> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 预订台生成订单号之后反写
     */
    @TableField("batch_no")
    private String batchNo;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 第三方订单号
     */
    @TableField("third_order_no")
    private String thirdOrderNo;
    @TableField("vip_name")
    private String vipName;
    @TableField("vip_phone")
    private String vipPhone;
    @TableField("vip_sex")
    private String vipSex;
    /**
     * 桌位类型 0大厅 1包间 3自定义类型
     */
    @TableField("table_type")
    private Integer tableType;
    @TableField("table_type_name")
    private String tableTypeName;
    private String remark;
    private Integer mealTypeId;
    private String mealTypeName;
    /**
     * 就餐时间
     */
    @TableField("resv_date")
    private Date resvDate;
    /**
     * 就餐人数
     */
    @TableField("resv_num")
    private Integer resvNum;
    /**
     * 第三方订单状态
     */
    private Integer status;
    /**
     * 0为未读 1为已读
     */
    private Integer flag;
    /**
     * 来源wx  koubei  meituan
     */
    private String source;
    /**
     * 微信公众号关联客户的id
     */
    private String openId;
    /**
     * 处理结果 0未处理 1接受 2拒绝
     */
    private Integer result;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
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

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
    }

    public Integer getTableType() {
        return tableType;
    }

    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }

    public Integer getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(Integer mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public String getTableTypeName() {
        return tableTypeName;
    }

    public void setTableTypeName(String tableTypeName) {
        this.tableTypeName = tableTypeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getResvDate() {
        return resvDate;
    }

    public void setResvDate(Date resvDate) {
        this.resvDate = resvDate;
    }

    public Integer getResvNum() {
        return resvNum;
    }

    public void setResvNum(Integer resvNum) {
        this.resvNum = resvNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResvOrderThird{" +
        "id=" + id +
        ", batchNo=" + batchNo +
        ", businessId=" + businessId +
        ", thirdOrderNo=" + thirdOrderNo +
        ", vipName=" + vipName +
        ", vipPhone=" + vipPhone +
        ", vipSex=" + vipSex +
        ", tableType=" + tableType +
        ", tableTypeName=" + tableTypeName +
        ", remark=" + remark +
        ", resvDate=" + resvDate +
        ", resvNum=" + resvNum +
        ", mealTypeId=" + mealTypeId +
        ", mealTypeName=" + mealTypeName +
        ", status=" + status +
        ", flag=" + flag +
        ", source=" + source +
        ", result=" + result +
        ", openId=" + openId +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
