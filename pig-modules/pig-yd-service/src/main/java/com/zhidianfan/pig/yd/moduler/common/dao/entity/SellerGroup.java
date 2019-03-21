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
 * @since 2018-12-10
 */
@TableName("seller_group")
public class SellerGroup extends Model<SellerGroup> {

    private static final long serialVersionUID = 1L;

    @TableField("group_code")
    private String groupCode;
    @TableField("group_name")
    private String groupName;
    @TableField("business_id")
    private Integer businessId;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("dingding_use")
    private Byte dingdingUse;


    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Byte getDingdingUse() {
        return dingdingUse;
    }

    public void setDingdingUse(Byte dingdingUse) {
        this.dingdingUse = dingdingUse;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SellerGroup{" +
        "groupCode=" + groupCode +
        ", groupName=" + groupName +
        ", businessId=" + businessId +
        ", id=" + id +
                ", dingdingUse=" + dingdingUse +
        "}";
    }
}
