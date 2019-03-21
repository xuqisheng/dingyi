package com.zhidianfan.pig.yd.moduler.sms.dto;

import lombok.NonNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 短信外部对象
 *
 * @author wangyz
 * @date 2017年12月07日15:45:38
 */
public class MessageDTO {

    /**
     * 酒店id
     */
    @NonNull
    private Long businessId;

    /**
     * 模板类型
     */
    @NonNull
    private Integer templateType;

    /**
     * 手机号码
     */
    @NotBlank
    private String phone;

    /**
     * 预定类型
     */
    @NonNull
    private Integer resvOrderTypeId;

    /**
     * 订单号
     */
    @NotBlank
    private String resvOrder;

    /**
     * 桌位名称 用于预定和入座消息
     */
    @NotNull
    private List<Map<String, Object>> checkedTables;

    /**
     * 旧桌
     */
    @NotBlank
    private String oldTableName;

    /**
     * 旧区域
     */
    @NotBlank
    private String oldTableAreaName;


    public MessageDTO() {
        super();
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getResvOrderTypeId() {
        return resvOrderTypeId;
    }

    public void setResvOrderTypeId(Integer resvOrderTypeId) {
        this.resvOrderTypeId = resvOrderTypeId;
    }

    public String getResvOrder() {
        return resvOrder;
    }

    public void setResvOrder(String resvOrder) {
        this.resvOrder = resvOrder;
    }

    public List<Map<String, Object>> getCheckedTables() {
        return checkedTables;
    }

    public void setCheckedTables(List<Map<String, Object>> checkedTables) {
        this.checkedTables = checkedTables;
    }

    public String getOldTableName() {
        return oldTableName;
    }

    public void setOldTableName(String oldTableName) {
        this.oldTableName = oldTableName;
    }

    public String getOldTableAreaName() {
        return oldTableAreaName;
    }

    public void setOldTableAreaName(String oldTableAreaName) {
        this.oldTableAreaName = oldTableAreaName;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "businessId=" + businessId +
                ", templateType=" + templateType +
                ", phone='" + phone + '\'' +
                ", resvOrderTypeId=" + resvOrderTypeId +
                ", resvOrder='" + resvOrder + '\'' +
                ", checkedTables=" + checkedTables +
                ", oldTableName='" + oldTableName + '\'' +
                ", oldTableAreaName='" + oldTableAreaName + '\'' +
                '}';
    }
}
