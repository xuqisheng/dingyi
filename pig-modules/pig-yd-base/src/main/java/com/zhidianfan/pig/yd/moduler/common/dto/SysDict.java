package com.zhidianfan.pig.yd.moduler.common.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author lengleng
 * @since 2017-11-19
 */
@Data
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	private Integer id;
    /**
     * 数据值
     */
	private String value;
    /**
     * 标签名
     */
	private String label;
    /**
     * 类型
     */
	private String type;
    /**
     * 描述
     */
	private String description;
    /**
     * 排序（升序）
     */
	private BigDecimal sort;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 备注信息
     */
	private String remarks;
    /**
     * 删除标记
     */
	private String delFlag;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysDict{" +
			", id=" + id +
			", value=" + value +
			", label=" + label +
			", type=" + type +
			", description=" + description +
			", sort=" + sort +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", remarks=" + remarks +
			", delFlag=" + delFlag +
			"}";
	}
}
