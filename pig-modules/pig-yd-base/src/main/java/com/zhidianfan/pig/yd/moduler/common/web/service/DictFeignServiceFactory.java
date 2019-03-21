package com.zhidianfan.pig.yd.moduler.common.web.service;

import com.zhidianfan.pig.yd.moduler.common.dto.SysDict;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@Component
@Slf4j
public class DictFeignServiceFactory implements FallbackFactory<DictFeignService> {
    @Override
    public DictFeignService create(Throwable throwable) {
        return new DictFeignService() {
            /**
             * 获取指定码表
             *
             * @param type
             * @return
             */
            @Override
            public List<SysDict> findDictByType(String type) {
                log.error("调用{}.{}异常：{}","DictFeignService","findDictByType",type);
                return new ArrayList<>();
            }
        };
    }
}
