package com.zhidianfan.pig.yd.moduler.sms.dto.sms;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-26
 * @Time: 15:08
 */
@Data
public class NotificationDTO {

    private String vipPhone;


    private String status;


    private Date startSendTime;


    private Date endSendTime;


    private Integer businessId;
}
