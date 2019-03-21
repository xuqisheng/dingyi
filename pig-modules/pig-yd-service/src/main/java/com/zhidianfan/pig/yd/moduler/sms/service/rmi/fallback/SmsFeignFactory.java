package com.zhidianfan.pig.yd.moduler.sms.service.rmi.fallback;

import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.ClMsgParam;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.SmsSendResDTO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @Author Conan
 * @Description
 * @Date: 2018/9/11 0011 下午 3:13
 * @Modified By:
 */
@Component
public class SmsFeignFactory implements FallbackFactory<SmsFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public SmsFeign create(Throwable throwable) {
        log.error("SmsFeign调用异常：{}", throwable.getMessage());

        return new SmsFeign() {
            @Override
            public SmsSendResDTO sendNormalMsg(String phone, String msg) {
                log.error("调用异常 {}.{} ", "SmsFeign", "sendNormalMsg");
                return null;
            }

            @Override
            public SmsSendResDTO sendBatchNormalMsg(ClMsgParam clMsgParam) {
                log.error("调用异常 {}.{} ", "SmsFeign", "sendBatchNormalMsg");
                return null;
            }

            @Override
            public SmsSendResDTO sendmarkMsg(String phone, String msg) {
                log.error("调用异常 {}.{} ", "SmsFeign", "sendmarkMsg");
                return null;
            }

            @Override
            public SmsSendResDTO sendBatchmarkMsg(ClMsgParam clMsgParam) {
                log.error("调用异常 {}.{} ", "SmsFeign", "sendBatchmarkMsg");
                return null;
            }
        };
    }
}
