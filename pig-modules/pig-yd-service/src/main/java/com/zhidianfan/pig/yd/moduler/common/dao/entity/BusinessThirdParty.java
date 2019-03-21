package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 酒店和第三方平台关系表
 * </p>
 *
 * @author ljh
 * @since 2018-09-20
 */
@TableName("business_third_party")
public class BusinessThirdParty extends Model<BusinessThirdParty> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店id
     */
    @TableField("business_id")
    private Integer businessId;
    /**
     * 酒店名称
     */
    @TableField("business_name")
    private String businessName;
    /**
     * 第三方id
     */
    @TableField("third_party_id")
    private Integer thirdPartyId;
    /**
     * 第三方名称
     */
    @TableField("third_party_name")
    private String thirdPartyName;
    /**
     * 合同起始时间
     */
    @TableField("contract_start")
    private Date contractStart;
    /**
     * 合同结束时间
     */
    @TableField("contract_end")
    private Date contractEnd;
    /**
     * 0无效1启动
     */
    private Integer status;

    /**
     * 排序
     */
    @TableField("`sort`")
    private Integer sort;


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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Integer thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public Date getContractStart() {
        return contractStart;
    }

    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusinessThirdParty{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", businessName=" + businessName +
                ", thirdPartyId=" + thirdPartyId +
                ", thirdPartyName=" + thirdPartyName +
                ", contractStart=" + contractStart +
                ", contractEnd=" + contractEnd +
                ", status=" + status +
                ", sort=" + sort +
                "}";
    }
}
