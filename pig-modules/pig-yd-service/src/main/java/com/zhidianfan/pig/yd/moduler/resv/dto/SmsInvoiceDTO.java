package com.zhidianfan.pig.yd.moduler.resv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-09
 * @Time: 14:39
 */
@Data
public class SmsInvoiceDTO {

    @ApiModelProperty("修改时递交")
    private Integer id;

    @NotNull(message = "商户id不能为空")
    @ApiModelProperty("查看时递交")
    private Integer businessId;


    private String invoiceCompany;
    @ApiModelProperty(" 发票类型 1:增值税普通发票   2:增值税专用发票")
    private Integer invoiceType;

    @ApiModelProperty("收件人")
    private String mailingPerson;

    private String mailingPhone;
    @ApiModelProperty("地址")
    private String mailingAddress;

    private String zipCode;

    @ApiModelProperty("备注")
    private String desc;

    @ApiModelProperty("纳税人识别号")
    private String taxpayerNumber;
    @ApiModelProperty("发票地址")
    private String invoiceAddress;
    @ApiModelProperty("发票银行")
    private String invoiceBank;

    @ApiModelProperty("账号")
    private String account;

    @NotNull(message = "操作必填")
    @ApiModelProperty("操作 1查看 2保存")
    private Byte operation;

}
