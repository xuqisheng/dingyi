package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 第三方平台登记表
 * </p>
 *
 * @author ljh
 * @since 2018-09-20
 */
@TableName("third_party")
public class ThirdParty extends Model<ThirdParty> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 0无效1有效
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "ThirdParty{" +
        "id=" + id +
        ", name=" + name +
        ", status=" + status +
        "}";
    }
}
