package com.zhidianfan.pig.push.feign.fallback;

import com.zhidianfan.pig.common.entity.SysDict;
import com.zhidianfan.pig.push.feign.DictFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return new DictFeign() {
            @Override
            public List<SysDict> findDictByType(String type) {
                log.error("调用异常 {}.{} ", "DictFeign", "findDictByType");
                return null;
            }
        };
    }
}
