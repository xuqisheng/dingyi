package com.zhidianfan.pig.user.dao.entity;

import java.util.Date;
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
 * @author qqx
 * @since 2018-11-21
 */
@TableName("agent")
public class Agent extends Model<Agent> {

    private static final long serialVersionUID = 1L;

    /**
     * 代理商id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 微信openid
     */
    @TableField("open_id")
    private String openId;
    /**
     * 代理商名称
     */
    @TableField("agent_name")
    private String agentName;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 1 独家城市代理 2普通
     */
    @TableField("agent_level")
    private Integer agentLevel;
    /**
     * 合同编号
     */
    @TableField("contract_no")
    private String contractNo;
    /**
     * 所属省份id
     */
    @TableField("province_id")
    private Integer provinceId;
    /**
     * 所属城市id
     */
    @TableField("city_id")
    private Integer cityId;
    @TableField("area_id")
    private Integer areaId;
    @TableField("province_name")
    private String provinceName;
    @TableField("city_name")
    private String cityName;
    @TableField("area_name")
    private String areaName;
    /**
     * 代理商所属类型(0个人、1公司、2自销)
     */
    private Integer type;
    /**
     * 代理商地址
     */
    @TableField("agent_address")
    private String agentAddress;
    /**
     * 代理商手机号
     */
    @TableField("agent_phone")
    private String agentPhone;
    /**
     * 营业执照url
     */
    @TableField("licence_url")
    private String licenceUrl;
    /**
     * 新增时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 联系人名称
     */
    @TableField("agent_manager")
    private String agentManager;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 0 待审核 1提供 2拒绝 3停用
     */
    private Integer status;
    /**
     * 审批时间
     */
    @TableField("review_time")
    private Date reviewTime;
    /**
     * 审批人
     */
    @TableField("review_user")
    private String reviewUser;
    private String tag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(Integer agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getLicenceUrl() {
        return licenceUrl;
    }

    public void setLicenceUrl(String licenceUrl) {
        this.licenceUrl = licenceUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAgentManager() {
        return agentManager;
    }

    public void setAgentManager(String agentManager) {
        this.agentManager = agentManager;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Agent{" +
        "id=" + id +
        ", openId=" + openId +
        ", agentName=" + agentName +
        ", username=" + username +
        ", password=" + password +
        ", agentLevel=" + agentLevel +
        ", contractNo=" + contractNo +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", areaId=" + areaId +
        ", provinceName=" + provinceName +
        ", cityName=" + cityName +
        ", areaName=" + areaName +
        ", type=" + type +
        ", agentAddress=" + agentAddress +
        ", agentPhone=" + agentPhone +
        ", licenceUrl=" + licenceUrl +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", agentManager=" + agentManager +
        ", remarks=" + remarks +
        ", status=" + status +
        ", reviewTime=" + reviewTime +
        ", reviewUser=" + reviewUser +
        ", tag=" + tag +
        "}";
    }
}
