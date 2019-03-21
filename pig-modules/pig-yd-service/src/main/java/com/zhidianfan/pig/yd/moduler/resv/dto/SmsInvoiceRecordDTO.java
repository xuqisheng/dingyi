package com.zhidianfan.pig.yd.moduler.resv.dto;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljh
 * @since 2018-11-09
 */
@Data
public class SmsInvoiceRecordDTO {



    /**
     * 充值记录id
     */
     private Integer id;
    /**
     * 发票金额
     */
    @ApiModelProperty("发票金额")
    private Double invoiceAmount;
     private String invoiceCompany;
    /**
     * 发票类型 1:增值税普通发票   2:增值税专用发票
     */
    @ApiModelProperty(" 发票类型 1:增值税普通发票   2:增值税专用发票")
    private Integer invoiceType;

    private String mailingPerson;

    private String mailingPhone;

    private String mailingAddress;

    private String zipCode;

    private String taxpayerNumber;

    private String invoiceAddress;

    private String invoiceBank;

    private String invoicePhone;

    private String account;
    private String desc;


    @NotNull(message = "商户id不能为空")
    private Integer businessId;
    /**
     * 集团id
     */
    @ApiModelProperty("集团id")
    private Integer brandId;
    /**
     * 充值记录id
     */
    @NotNull(message = "充值记录id不能为空")
    @ApiModelProperty("充值记录id")
    private Integer rechargeLogId;



}
