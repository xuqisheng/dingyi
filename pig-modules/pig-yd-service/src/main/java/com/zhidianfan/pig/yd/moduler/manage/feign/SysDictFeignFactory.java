package com.zhidianfan.pig.yd.moduler.manage.feign;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */

@Service
public class SysDictFeignFactory implements FallbackFactory<SysDictFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public SysDictFeign create(Throwable throwable) {
        log.info(throwable.getMessage());
        return new SysDictFeign() {
            @Override
            public String getDict(String value,String type) {
                log.error("调用 getDict 异常，type：{}", type);
                return null;
            }

        };
    }
}
