package com.zhidianfan.pig.yd.moduler.sms.dto;

import lombok.Data;

/**
 * @Author: huzp
 * @Date: 2018/11/20 14:55
 * @DESCRIPTION 排队催促信息
 */
@Data
public class LineMessageDTO {

    private String phone;

    private String lineNo;
}
