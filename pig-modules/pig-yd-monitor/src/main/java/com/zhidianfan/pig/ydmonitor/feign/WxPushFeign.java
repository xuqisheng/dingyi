package com.zhidianfan.pig.ydmonitor.feign;

import com.zhidianfan.pig.common.dto.WxBatchPushDTO;
import com.zhidianfan.pig.common.dto.WxSinglePushDTO;
import com.zhidianfan.pig.ydmonitor.feign.fallback.WxPushFeignFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@FeignClient(name = "pig-yd-push", fallbackFactory = WxPushFeignFallbackFactory.class)
@RequestMapping("/wx")
public interface WxPushFeign {
    @PostMapping("/push/single")
    ResponseEntity pushToSingle(WxSinglePushDTO pushDTO);

    @PostMapping("/push/batch")
    ResponseEntity pushToBatch(WxBatchPushDTO pushDTO);
}
