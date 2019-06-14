package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 客户价值基本信息表
 * </p>
 *
 * @author 施杰灵
 * @since 2019-05-21
 */
@TableName("customer_value_base_info")
public class CustomerValueBaseInfo extends Model<CustomerValueBaseInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 单位
     */
    private String company;
    /**
     * 总消费次数
     */
    @TableField("total_consume_count")
    private Integer totalConsumeCount;
    /**
     * 总消费金额，单位:分
     */
    @TableField("total_consume_amount")
    private Integer totalConsumeAmount;
    /**
     * 最近就餐时间
     */
    @TableField("last_consume_time")
    private Date lastConsumeTime;
    /**
     * 客户资料完善度
     */
    @TableField("custom_profile")
    private String customProfile;
    /**
     * 一级分类
     */
    @TableField("first_level")
    private String firstLevel;
    /**
     * 细分价值
     */
    @TableField("classify_value")
    private String classifyValue;
    /**
     * 自定义分类
     */
    @TableField("custom_classify")
    private String customClassify;
    /**
     * 所属营销
     */
    @TableField("app_user_name")
    private String appUserName;
    /**
     * 创建人 user_id
     */
    @TableField("create_user_id")
    private Long createUserId;
    /**
     * 创建时间
     */
    @TableField("cteate_time")
    private Date cteateTime;
    /**
     * 更新人 user_id
     */
    @TableField("update_user_id")
    private Long updateUserId;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getTotalConsumeCount() {
        return totalConsumeCount;
    }

    public void setTotalConsumeCount(Integer totalConsumeCount) {
        this.totalConsumeCount = totalConsumeCount;
    }

    public Integer getTotalConsumeAmount() {
        return totalConsumeAmount;
    }

    public void setTotalConsumeAmount(Integer totalConsumeAmount) {
        this.totalConsumeAmount = totalConsumeAmount;
    }

    public Date getLastConsumeTime() {
        return lastConsumeTime;
    }

    public void setLastConsumeTime(Date lastConsumeTime) {
        this.lastConsumeTime = lastConsumeTime;
    }

    public String getCustomProfile() {
        return customProfile;
    }

    public void setCustomProfile(String customProfile) {
        this.customProfile = customProfile;
    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    public String getClassifyValue() {
        return classifyValue;
    }

    public void setClassifyValue(String classifyValue) {
        this.classifyValue = classifyValue;
    }

    public String getCustomClassify() {
        return customClassify;
    }

    public void setCustomClassify(String customClassify) {
        this.customClassify = customClassify;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCteateTime() {
        return cteateTime;
    }

    public void setCteateTime(Date cteateTime) {
        this.cteateTime = cteateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomerValueBaseInfo{" +
        "id=" + id +
        ", name=" + name +
        ", sex=" + sex +
        ", phone=" + phone +
        ", age=" + age +
        ", company=" + company +
        ", totalConsumeCount=" + totalConsumeCount +
        ", totalConsumeAmount=" + totalConsumeAmount +
        ", lastConsumeTime=" + lastConsumeTime +
        ", customProfile=" + customProfile +
        ", firstLevel=" + firstLevel +
        ", classifyValue=" + classifyValue +
        ", customClassify=" + customClassify +
        ", appUserName=" + appUserName +
        ", createUserId=" + createUserId +
        ", cteateTime=" + cteateTime +
        ", updateUserId=" + updateUserId +
        ", updateTime=" + updateTime +
        "}";
    }
}
