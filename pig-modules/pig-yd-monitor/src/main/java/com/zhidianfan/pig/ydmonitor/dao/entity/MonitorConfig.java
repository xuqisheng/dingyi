package com.zhidianfan.pig.ydmonitor.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoubeibei
 * @since 2018-11-07
 */
@TableName("monitor_config")
public class MonitorConfig extends Model<MonitorConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 自动编号,主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 配置名称
     */
    @TableField("config_name")
    private String configName;
    /**
     * 配置值
     */
    @TableField("config_value")
    private String configValue;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MonitorConfig{" +
        "id=" + id +
        ", configName=" + configName +
        ", configValue=" + configValue +
        "}";
    }
}
