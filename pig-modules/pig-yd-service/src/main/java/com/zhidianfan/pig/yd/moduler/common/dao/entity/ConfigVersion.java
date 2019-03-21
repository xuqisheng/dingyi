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
 * 客户端版本配置表 config_version
 * </p>
 *
 * @author 李凌峰
 * @since 2019-03-12
 */
@TableName("config_version")
public class ConfigVersion extends Model<ConfigVersion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * MOBILE-手机端 ANDROID-安卓电话机 PAD-预订台
     */
    @TableField("client_type")
    private String clientType;
    /**
     * 客户端名
     */
    @TableField("client_name")
    private String clientName;
    /**
     * 当前允许使用软件版本
     */
    @TableField("enable_version")
    private String enableVersion;
    /**
     * 当前最新版本
     */
    @TableField("lastest_version")
    private String lastestVersion;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEnableVersion() {
        return enableVersion;
    }

    public void setEnableVersion(String enableVersion) {
        this.enableVersion = enableVersion;
    }

    public String getLastestVersion() {
        return lastestVersion;
    }

    public void setLastestVersion(String lastestVersion) {
        this.lastestVersion = lastestVersion;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ConfigVersion{" +
        "id=" + id +
        ", clientType=" + clientType +
        ", clientName=" + clientName +
        ", enableVersion=" + enableVersion +
        ", lastestVersion=" + lastestVersion +
        ", createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        "}";
    }
}
