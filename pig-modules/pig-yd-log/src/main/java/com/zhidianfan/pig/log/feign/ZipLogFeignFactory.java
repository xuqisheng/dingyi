package com.zhidianfan.pig.log.feign;

import com.zhidianfan.pig.log.dto.CommonRes;
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
public class ZipLogFeignFactory implements FallbackFactory<ZipLogFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ZipLogFeign create(Throwable throwable) {
        log.info(throwable.getMessage());
        return new ZipLogFeign() {
            @Override
            public CommonRes deleteAllZipkinData(String taskId) {
                log.error("调用 deleteAllZipkinData 异常，taskId：{}", taskId);
                return new CommonRes(500,throwable.getMessage());
            }

            @Override
            public CommonRes deleteAllSysLogData(String taskId) {
                log.error("调用 deleteAllSysLogData 异常，taskId：{}", taskId);
                return new CommonRes(500,throwable.getMessage());
            }

        };
    }
}
