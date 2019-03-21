package com.zhidianfan.pig.push.feign;

import com.zhidianfan.pig.push.feign.fallback.BasePushFeignFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/23
 * @Modified By:
 */
@FeignClient(name = "pig-yd-base", fallbackFactory = BasePushFeignFallbackFactory.class)
public interface BasePushFeign {
    @PostMapping(value = "/push/msg")
    ResponseEntity pushMsg(@RequestParam(name = "type") Integer type, @RequestParam(name = "username") String username, @RequestParam(name = "msgSeq") String msgSeq, @RequestParam(name = "businessId") Integer businessId, @RequestParam(name = "msg") String msg);
}
