package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2018-10-19
 */
@ApiModel(value="客户信息")
@TableName("vip")
public class Vip extends Model<Vip> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="客户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value="酒店id")
    @TableField("business_id")
    private Integer businessId;
    @ApiModelProperty(value="酒店名称")
    @TableField("business_name")
    private String businessName;
    @ApiModelProperty(value="客户名字")
    @TableField("vip_name")
    private String vipName;
    @ApiModelProperty(value="客户号码")
    @TableField("vip_phone")
    private String vipPhone;
    @TableField("vip_phone2")
    private String vipPhone2;
    @ApiModelProperty(value="客户公司")
    @TableField("vip_company")
    private String vipCompany;
    @ApiModelProperty(value="客户职位")
    @TableField("vip_postion")
    private String vipPostion;

    @ApiModelProperty(value="客户性别")
    @TableField("vip_sex")
    private String vipSex;
    @ApiModelProperty(value="客户生日")
    @TableField("vip_birthday")
    private String vipBirthday;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @ApiModelProperty(value="客户地址")
    @TableField("vip_address")
    private String vipAddress;
    private String remark;
    @TableField("short_phone_num")
    private String shortPhoneNum;
    private String telephone;
    @ApiModelProperty(value="客户习惯")
    private String hobby;
    private String detest;
    private String spells;
    @TableField("family_name_spell")
    private String familyNameSpell;
    @ApiModelProperty(value="客户价值id")
    @TableField("vip_value_id")
    private Integer vipValueId;
    @ApiModelProperty(value="客户价值名称")
    @TableField("vip_value_name")
    private String vipValueName;
    @ApiModelProperty(value="客户分类id")
    @TableField("vip_class_id")
    private Integer vipClassId;
    @ApiModelProperty(value="客户分类名称")
    @TableField("vip_class_name")
    private String vipClassName;
    @TableField("app_user_id")
    private Integer appUserId;
    @TableField("image_url")
    private String imageUrl;
    private String tag;
    @TableField("vip_birthday_nl")
    private String vipBirthdayNl;
    @TableField("target_id")
    private Integer targetId;
    /**
     * 集团分类id
     */
    @TableField("vip_brand_class_id")
    private Integer vipBrandClassId;
    @TableField("vip_brand_class_name")
    private String vipBrandClassName;

    @TableField("birth_flag")
    private Integer birthFlag;

    @TableField("hide_birthday_year")
    private Integer hideBirthdayYear;

    @TableField("next_vip_birthday")
    private Date nextVipBirthday;

    @TableField("is_leap")
    private Integer isLeap;

    @TableField("allergen")
    private String allergen;


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

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getVipPhone2() {
        return vipPhone2;
    }

    public void setVipPhone2(String vipPhone2) {
        this.vipPhone2 = vipPhone2;
    }

    public String getVipCompany() {
        return vipCompany;
    }

    public void setVipCompany(String vipCompany) {
        this.vipCompany = vipCompany;
    }

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
    }

    public String getVipBirthday() {
        return vipBirthday;
    }

    public void setVipBirthday(String vipBirthday) {
        this.vipBirthday = vipBirthday;
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

    public String getVipAddress() {
        return vipAddress;
    }

    public void setVipAddress(String vipAddress) {
        this.vipAddress = vipAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShortPhoneNum() {
        return shortPhoneNum;
    }

    public void setShortPhoneNum(String shortPhoneNum) {
        this.shortPhoneNum = shortPhoneNum;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getDetest() {
        return detest;
    }

    public void setDetest(String detest) {
        this.detest = detest;
    }

    public String getSpells() {
        return spells;
    }

    public void setSpells(String spells) {
        this.spells = spells;
    }

    public String getFamilyNameSpell() {
        return familyNameSpell;
    }

    public void setFamilyNameSpell(String familyNameSpell) {
        this.familyNameSpell = familyNameSpell;
    }

    public Integer getVipValueId() {
        return vipValueId;
    }

    public void setVipValueId(Integer vipValueId) {
        this.vipValueId = vipValueId;
    }

    public String getVipValueName() {
        return vipValueName;
    }

    public void setVipValueName(String vipValueName) {
        this.vipValueName = vipValueName;
    }

    public Integer getVipClassId() {
        return vipClassId;
    }

    public void setVipClassId(Integer vipClassId) {
        this.vipClassId = vipClassId;
    }

    public String getVipClassName() {
        return vipClassName;
    }

    public void setVipClassName(String vipClassName) {
        this.vipClassName = vipClassName;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVipBirthdayNl() {
        return vipBirthdayNl;
    }

    public void setVipBirthdayNl(String vipBirthdayNl) {
        this.vipBirthdayNl = vipBirthdayNl;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getVipBrandClassId() {
        return vipBrandClassId;
    }

    public void setVipBrandClassId(Integer vipBrandClassId) {
        this.vipBrandClassId = vipBrandClassId;
    }

    public String getVipBrandClassName() {
        return vipBrandClassName;
    }

    public void setVipBrandClassName(String vipBrandClassName) {
        this.vipBrandClassName = vipBrandClassName;
    }

    public String getVipPostion() {
        return vipPostion;
    }

    public void setVipPostion(String vipPostion) {
        this.vipPostion = vipPostion;
    }

    public Integer getBirthFlag() {
        return birthFlag;
    }

    public void setBirthFlag(Integer birthFlag) {
        this.birthFlag = birthFlag;
    }


    public Integer getHideBirthdayYear() {
        return hideBirthdayYear;
    }

    public void setHideBirthdayYear(Integer hideBirthdayYear) {
        this.hideBirthdayYear = hideBirthdayYear;
    }

    public Date getNextVipBirthday() {
        return nextVipBirthday;
    }

    public void setNextVipBirthday(Date nextVipBirthday) {
        this.nextVipBirthday = nextVipBirthday;
    }

    public Integer getIsLeap() {
        return isLeap;
    }

    public void setIsLeap(Integer isLeap) {
        this.isLeap = isLeap;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Vip{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", vipName='" + vipName + '\'' +
                ", vipPhone='" + vipPhone + '\'' +
                ", vipPhone2='" + vipPhone2 + '\'' +
                ", vipCompany='" + vipCompany + '\'' +
                ", vipPostion='" + vipPostion + '\'' +
                ", vipSex='" + vipSex + '\'' +
                ", vipBirthday='" + vipBirthday + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", vipAddress='" + vipAddress + '\'' +
                ", remark='" + remark + '\'' +
                ", shortPhoneNum='" + shortPhoneNum + '\'' +
                ", telephone='" + telephone + '\'' +
                ", hobby='" + hobby + '\'' +
                ", detest='" + detest + '\'' +
                ", spells='" + spells + '\'' +
                ", familyNameSpell='" + familyNameSpell + '\'' +
                ", vipValueId=" + vipValueId +
                ", vipValueName='" + vipValueName + '\'' +
                ", vipClassId=" + vipClassId +
                ", vipClassName='" + vipClassName + '\'' +
                ", appUserId=" + appUserId +
                ", imageUrl='" + imageUrl + '\'' +
                ", tag='" + tag + '\'' +
                ", vipBirthdayNl='" + vipBirthdayNl + '\'' +
                ", targetId=" + targetId +
                ", vipBrandClassId=" + vipBrandClassId +
                ", vipBrandClassName='" + vipBrandClassName + '\'' +
                ", birthFlag=" + birthFlag +
                ", hideBirthdayYear=" + hideBirthdayYear +
                ", nextVipBirthday=" + nextVipBirthday +
                ", isLeap=" + isLeap +
                ", allergen=" + allergen +
                '}';
    }
}
