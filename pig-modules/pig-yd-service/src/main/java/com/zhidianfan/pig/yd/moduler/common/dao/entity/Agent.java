package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qqx
 * @since 2018-11-27
 */
@TableName("agent")
public class Agent extends Model<Agent> {

    private static final long serialVersionUID = 1L;

    /**
     * 代理商id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("open_id")
    private String openId;
    @TableField("agent_name")
    private String agentName;
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
    /**
     * 所属省份
     */
    @TableField("province_name")
    private String provinceName;
    /**
     * 所属城市
     */
    @TableField("city_name")
    private String cityName;
    /**
     * 代理商所属类型(0个人、1公司、2自销)
     */
    private Integer type;
    /**
     * 地址
     */
    private String address;
    /**
     * 代理商手机号
     */
    @TableField("phone_num")
    private String phoneNum;
    /**
     * 营业执照url
     */
    @TableField("licence_url")
    private String licenceUrl;
    /**
     * 0 待审核 1提供 2拒绝 3停用
     */
    private String status;
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
    private String linkman;
    /**
     * 结算价格
     */
    @TableField("settlement_price")
    private String settlementPrice;
    private String remarks;
    private String username;
    private String password;
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
    /**
     * 1 pad版本  2 电话机版本
     */
    private Integer device;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLicenceUrl() {
        return licenceUrl;
    }

    public void setLicenceUrl(String licenceUrl) {
        this.licenceUrl = licenceUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(String settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
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
        ", agentLevel=" + agentLevel +
        ", contractNo=" + contractNo +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", provinceName=" + provinceName +
        ", cityName=" + cityName +
        ", type=" + type +
        ", address=" + address +
        ", phoneNum=" + phoneNum +
        ", licenceUrl=" + licenceUrl +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", linkman=" + linkman +
        ", settlementPrice=" + settlementPrice +
        ", remarks=" + remarks +
        ", username=" + username +
        ", password=" + password +
        ", reviewTime=" + reviewTime +
        ", reviewUser=" + reviewUser +
        ", tag=" + tag +
        ", device=" + device +
        "}";
    }
}
