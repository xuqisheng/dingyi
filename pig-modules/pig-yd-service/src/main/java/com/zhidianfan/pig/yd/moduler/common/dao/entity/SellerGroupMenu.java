package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujh
 * @since 2019-03-08
 */
@TableName("seller_group_menu")
public class SellerGroupMenu extends Model<SellerGroupMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("group_code")
    private String groupCode;
    @TableField("menu_code")
    private Integer menuCode;
    @TableField("client_type")
    private String clientType;


    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Integer menuCode) {
        this.menuCode = menuCode;
    }

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

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "SellerGroupMenu{" +
        "groupCode=" + groupCode +
        ", menuCode=" + menuCode +
                ", id=" + id +
                ", clientType=" + clientType +
        "}";
    }
}
