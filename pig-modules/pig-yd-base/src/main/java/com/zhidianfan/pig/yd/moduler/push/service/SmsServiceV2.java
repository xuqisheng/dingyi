package com.zhidianfan.pig.yd.moduler.push.service;

import com.zhidianfan.pig.yd.moduler.common.dto.Tip;

import java.util.List;

/**
 * 短信发送接口
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/9/30
 * @Modified By:
 */

public interface SmsServiceV2 {

    /**
     * 单笔短信发送
     *
     * @param phone
     * @param msg
     * @param smsType 短信类型：普通短信、营销短信等等
     * @return
     */
    Tip sendMsg(String phone, String msg, String smsType);


    /**
     * 短信群发
     *
     * @param phones
     * @param msg
     * @param smsType 短信类型：普通短信、营销短信等等
     * @return
     */
    Tip sendMsg(List<String> phones, String msg, String smsType);

}
