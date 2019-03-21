package com.zhidianfan.pig.user.dao.entity;

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
 * @author ljh
 * @since 2018-11-15
 */
@TableName("table_type")
public class TableType extends Model<TableType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private Integer businessId;
    /**
     * 桌位类型
     */
    private String name;
    /**
     * 最少人数
     */
    @TableField("min_people_num")
    private Integer minPeopleNum;
    /**
     * 最大人数
     */
    @TableField("max_people_num")
    private Integer maxPeopleNum;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 0关闭1启用
     */
    private Integer status;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinPeopleNum() {
        return minPeopleNum;
    }

    public void setMinPeopleNum(Integer minPeopleNum) {
        this.minPeopleNum = minPeopleNum;
    }

    public Integer getMaxPeopleNum() {
        return maxPeopleNum;
    }

    public void setMaxPeopleNum(Integer maxPeopleNum) {
        this.maxPeopleNum = maxPeopleNum;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TableType{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", name=" + name +
        ", minPeopleNum=" + minPeopleNum +
        ", maxPeopleNum=" + maxPeopleNum +
        ", sort=" + sort +
        ", status=" + status +
        "}";
    }
}
