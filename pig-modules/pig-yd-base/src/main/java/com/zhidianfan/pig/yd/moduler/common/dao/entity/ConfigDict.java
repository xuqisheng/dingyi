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
@TableName("config_dict")
public class ConfigDict extends Model<ConfigDict> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String key;
    private String value;
    @TableField("sort_num")
    private Integer sortNum;
    /**
     * 0停用  1启用
     */
    @TableField("is_use")
    private Integer isUse;
    /**
     * 注释
     */
    private String note;
    @TableField("parent_id")
    private Integer parentId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ConfigDict{" +
        "id=" + id +
        ", key=" + key +
        ", value=" + value +
        ", sortNum=" + sortNum +
        ", isUse=" + isUse +
        ", note=" + note +
        ", parentId=" + parentId +
        "}";
    }
}
