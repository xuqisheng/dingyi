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
 * @author huzp
 * @since 2019-06-10
 */
@TableName("sync_interface")
public class SyncInterface extends Model<SyncInterface> {

    private static final long serialVersionUID = 1L;

    /**
     * 接口编号
     */
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;
    @TableField("type_name")
    private String typeName;


    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    protected Serializable pkVal() {
        return this.typeId;
    }

    @Override
    public String toString() {
        return "SyncInterface{" +
        "typeId=" + typeId +
        ", typeName=" + typeName +
        "}";
    }
}
