package com.zhidianfan.pig.yd.moduler.resv.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SmsRecordBO {

    /**
     * 获取途径
     */
    private String access;

    /**
     * 单号id
     */
    private String orderNo;

    /**
     * 短信充值条数
     */
    private Integer smscount;

    /**
     * 支付金额
     */
    private Double payamount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE)
    private Date updateAt;


    @ApiModelProperty("0.未申请 ，1.申请开票 ， 2.发票完成")
    private Integer invoiceStatus;


    @ApiModelProperty("短信开票记录id")
    private Integer invoiceRecordId;


    private Integer id;
}
