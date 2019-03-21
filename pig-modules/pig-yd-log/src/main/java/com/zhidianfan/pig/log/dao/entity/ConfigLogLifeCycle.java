package com.zhidianfan.pig.log.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 日志表生命周期配置表
 * </p>
 *
 * @author sherry
 * @since 2018-10-26
 */
@TableName("config_log_life_cycle")
public class ConfigLogLifeCycle extends Model<ConfigLogLifeCycle> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 表名
     */
    @TableField("table_name")
    private String tableName;
    /**
     * 配置形式
1-清空该表
2-保留最近n条记录
3-保留最近n条的数据
     */
    @TableField("config_type")
    private Integer configType;
    /**
     * 意义由config_type决定；条数或天数
     */
    private Integer config1;
    private String config2;
    @TableField("data_source_name")
    private String dataSourceName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    public Integer getConfig1() {
        return config1;
    }

    public void setConfig1(Integer config1) {
        this.config1 = config1;
    }

    public String getConfig2() {
        return config2;
    }

    public void setConfig2(String config2) {
        this.config2 = config2;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ConfigLogLifeCycle{" +
        "id=" + id +
        ", tableName=" + tableName +
        ", configType=" + configType +
        ", config1=" + config1 +
        ", dataSourceName=" + dataSourceName +
        ", config2=" + config2 +
        "}";
    }
}
