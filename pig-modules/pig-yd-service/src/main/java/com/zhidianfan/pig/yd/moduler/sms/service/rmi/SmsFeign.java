package com.zhidianfan.pig.yd.moduler.sms.service.rmi;

import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.ClMsgParam;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.SmsSendResDTO;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.fallback.SmsFeignFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author Conan
 * @Description
 * @Date: 2018/9/11 0011 下午 2:38
 * @Modified By:
 */
@FeignClient(name = "pig-yd-sms",fallbackFactory = SmsFeignFactory.class)
public interface SmsFeign {

    /**
     * 单笔普通短信发送
     *
     * @param phone
     * @param msg
     * @return
     */
    @PostMapping(value = "/sms/send", params = {"phone", "msg"})
    SmsSendResDTO sendNormalMsg(@RequestParam(name = "phone") String phone, @RequestParam(name = "msg") String msg);

    /**
     * 批量普通短信发送
     *
     * @param clMsgParam
     * @return
     */
    @PostMapping(value = "/sms/sendLot")
    SmsSendResDTO sendBatchNormalMsg(@RequestBody ClMsgParam clMsgParam);

    /**
     * 单笔营销短信发送
     *
     * @param phone
     * @param msg
     * @return
     */
    @PostMapping(value = "/send/v1", params = {"phone", "msg"})
    SmsSendResDTO sendmarkMsg(@RequestParam(name = "phone") String phone, @RequestParam(name = "msg") String msg);

    /**
     * 批量营销短信发送
     *
     * @param clMsgParam
     * @return
     */
    @PostMapping(value = "/sms/sendLot/v1")
    SmsSendResDTO sendBatchmarkMsg(@RequestBody ClMsgParam clMsgParam);
}
