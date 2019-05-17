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
 * @since 2019-05-16
 */
@TableName("vip_appraise")
public class VipAppraise extends Model<VipAppraise> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("businss_id")
    private Integer businessId;
    @TableField("vip_phone")
    private String vipPhone;
    /**
     * 菜品ID	
     */
    @TableField("dish_id")
    private Integer dishId;
    /**
     * 菜品评价选项	
     */
    @TableField("dish_options")
    private String dishOptions;
    /**
     * 服务员ID	
     */
    @TableField("server_id")
    private String serverId;
    /**
     * 服务员评分	
     */
    @TableField("server_score")
    private String serverScore;
    /**
     * 服务员评分选项	
     */
    @TableField("server_options")
    private String serverOptions;
    /**
     * 桌台号	
     */
    @TableField("table_id")
    private String tableId;
    /**
     * 消费总金额	
     */
    @TableField("tctotal_fee")
    private String tctotalFee;
    /**
     * 消费金额	
     */
    private String tcFee;
    /**
     * 消费流水号	
     */
    @TableField("tc_id")
    private String tcId;
    /**
     * 消费时间	
     */
    @TableField("tc_time")
    private Date tcTime;
    /**
     * 评论时间	
     */
    @TableField("cm_time")
    private Date cmTime;
    /**
     * 1 赞；2踩	
     */
    @TableField("dish_score")
    private Integer dishScore;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businssId) {
        this.businessId = businssId;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getDishOptions() {
        return dishOptions;
    }

    public void setDishOptions(String dishOptions) {
        this.dishOptions = dishOptions;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerScore() {
        return serverScore;
    }

    public void setServerScore(String serverScore) {
        this.serverScore = serverScore;
    }

    public String getServerOptions() {
        return serverOptions;
    }

    public void setServerOptions(String serverOptions) {
        this.serverOptions = serverOptions;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTctotalFee() {
        return tctotalFee;
    }

    public void setTctotalFee(String tctotalFee) {
        this.tctotalFee = tctotalFee;
    }

    public String getTcFee() {
        return tcFee;
    }

    public void setTcFee(String tcFee) {
        this.tcFee = tcFee;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public Date getTcTime() {
        return tcTime;
    }

    public void setTcTime(Date tcTime) {
        this.tcTime = tcTime;
    }

    public Date getCmTime() {
        return cmTime;
    }

    public void setCmTime(Date cmTime) {
        this.cmTime = cmTime;
    }

    public Integer getDishScore() {
        return dishScore;
    }

    public void setDishScore(Integer dishScore) {
        this.dishScore = dishScore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VipAppraise{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", vipPhone=" + vipPhone +
        ", dishId=" + dishId +
        ", dishOptions=" + dishOptions +
        ", serverId=" + serverId +
        ", serverScore=" + serverScore +
        ", serverOptions=" + serverOptions +
        ", tableId=" + tableId +
        ", tctotalFee=" + tctotalFee +
        ", tcFee=" + tcFee +
        ", tcId=" + tcId +
        ", tcTime=" + tcTime +
        ", cmTime=" + cmTime +
        ", dishScore=" + dishScore +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
