package com.zhidianfan.pig.yd.sms.service;

import com.zhidianfan.pig.yd.sms.dto.SmsSendResDTO;

/**
 * @Author: huzp
 * @Date: 2018/11/2 15:11
 * @DESCRIPTION 短信发送
 */
public interface SmsService {

    /**
     *  短信发送
     * @param phone 电话号码,多个号码使用,隔开
     * @param msg 短信信息
     * @param type 短信类型
     * @param smsId 短信id
     * @return
     */
    SmsSendResDTO sendMsg(String phone, String msg, String type, long smsId);


}
