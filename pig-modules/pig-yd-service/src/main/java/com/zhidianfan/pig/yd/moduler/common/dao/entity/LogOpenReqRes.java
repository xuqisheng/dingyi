package com.zhidianfan.pig.yd.moduler.common.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @author zhoubeibei
 * @since 2019-03-11
 */
@TableName("log_open_req_res")
public class LogOpenReqRes extends Model<LogOpenReqRes> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("business_id")
    private String businessId;
    @TableField("business_name")
    private String businessName;
    @TableField("req_url")
    private String reqUrl;
    @TableField("req_method")
    private String reqMethod;
    @TableField("req_arguments")
    private String reqArguments;
    @TableField("res_body")
    private String resBody;
    private String exception;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getReqArguments() {
        return reqArguments;
    }

    public void setReqArguments(String reqArguments) {
        this.reqArguments = reqArguments;
    }

    public String getResBody() {
        return resBody;
    }

    public void setResBody(String resBody) {
        this.resBody = resBody;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LogOpenReqRes{" +
        "id=" + id +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", reqUrl=" + reqUrl +
        ", reqMethod=" + reqMethod +
        ", reqArguments=" + reqArguments +
        ", resBody=" + resBody +
        ", exception=" + exception +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
