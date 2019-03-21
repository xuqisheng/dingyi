package com.zhidianfan.pig.ydmonitor.feign.fallback;

import com.zhidianfan.pig.common.dto.WxBatchPushDTO;
import com.zhidianfan.pig.common.dto.WxSinglePushDTO;
import com.zhidianfan.pig.ydmonitor.feign.WxPushFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@Slf4j
@Component
public class WxPushFeignFallbackFactory implements FallbackFactory<WxPushFeign> {

    @Override
    public WxPushFeign create(Throwable throwable) {
        return new WxPushFeign() {
            @Override
            public ResponseEntity pushToSingle(WxSinglePushDTO pushDTO) {
                log.error("调用异常 {}.{} ", "WxPushFeign", "pushToSingle");
                return null;
            }

            @Override
            public ResponseEntity pushToBatch(WxBatchPushDTO pushDTO) {
                log.error("调用异常 {}.{} ", "WxPushFeign", "pushToBatch");
                return null;
            }
        };
    }
}
