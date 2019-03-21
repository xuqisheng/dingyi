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
 * @author liujh
 * @since 2019-03-08
 */
@TableName("seller_menu")
public class SellerMenu extends Model<SellerMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_code", type = IdType.AUTO)
    private Integer menuCode;
    @TableField("menu_name")
    private String menuName;
    private Integer pcode;
    private String url;
    private Integer sort;
    private String isenable;
    private String icon;
    /**
     * 使用端 yd-manage,dd-manage
     */
    @TableField("client_name")
    private String clientName;


    public Integer getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Integer menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getPcode() {
        return pcode;
    }

    public void setPcode(Integer pcode) {
        this.pcode = pcode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsenable() {
        return isenable;
    }

    public void setIsenable(String isenable) {
        this.isenable = isenable;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    protected Serializable pkVal() {
        return this.menuCode;
    }

    @Override
    public String toString() {
        return "SellerMenu{" +
        "menuCode=" + menuCode +
        ", menuName=" + menuName +
        ", pcode=" + pcode +
        ", url=" + url +
        ", sort=" + sort +
        ", isenable=" + isenable +
        ", icon=" + icon +
        ", clientName=" + clientName +
        "}";
    }
}
