package com.zhidianfan.pig.ydmonitor.feign.fallback;

import com.zhidianfan.pig.ydmonitor.feign.DictFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@Slf4j
@Component
public class DictFeignFallbackFactory implements FallbackFactory<DictFeign> {
    @Override
    public DictFeign create(Throwable throwable) {
        return type -> {
            log.error("调用异常 {}.{} ", "DictFeign", "findDictByType");
            return null;
        };
    }
}
