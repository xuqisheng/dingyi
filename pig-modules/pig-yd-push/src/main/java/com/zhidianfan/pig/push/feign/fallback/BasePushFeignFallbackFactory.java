package com.zhidianfan.pig.push.feign.fallback;

import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.push.feign.BasePushFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/23
 * @Modified By:
 */
@Component
@Slf4j
public class BasePushFeignFallbackFactory implements FallbackFactory<BasePushFeign> {
    @Override
    public BasePushFeign create(Throwable throwable) {
        return new BasePushFeign() {
            @Override
            public ResponseEntity pushMsg(Integer type, String username, String msgSeq, Integer businessId, String msg) {
                log.error("调用异常 {}.{} ", "BasePushFeign", "pushMsg");
                log.error("调用异常信息:{}",throwable.getMessage());
                return ResponseEntity.ok(new ErrorTip(400, "推送失败"));
            }
        };
    }
}
