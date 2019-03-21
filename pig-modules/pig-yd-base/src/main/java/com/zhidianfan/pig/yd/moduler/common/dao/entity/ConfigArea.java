package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @author qqx
 * @since 2018-11-02
 */
@TableName("config_area")
public class ConfigArea extends Model<ConfigArea> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("area_code")
    private String areaCode;
    @TableField("area_name")
    private String areaName;
    @TableField("parent_code")
    private String parentCode;
    /**
     * 省市区类型，0省、1市、2区
     */
    private Integer type;
    @TableField("sout_num")
    private Integer soutNum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSoutNum() {
        return soutNum;
    }

    public void setSoutNum(Integer soutNum) {
        this.soutNum = soutNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ConfigArea{" +
        "id=" + id +
        ", areaCode=" + areaCode +
        ", areaName=" + areaName +
        ", parentCode=" + parentCode +
        ", type=" + type +
        ", soutNum=" + soutNum +
        "}";
    }
}
