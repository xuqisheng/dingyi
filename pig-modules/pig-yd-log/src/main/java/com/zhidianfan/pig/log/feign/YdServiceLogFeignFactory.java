package com.zhidianfan.pig.log.feign;

import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.Tip;
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
public class YdServiceLogFeignFactory implements FallbackFactory<YdServiceLogFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public YdServiceLogFeign create(Throwable throwable) {
        return new YdServiceLogFeign() {

            @Override
            public Tip deleteByConfig(String tableName, String filedName, Integer count) {
                return ErrorTip.ERROR_TIP;
            }
        };
    }
}
