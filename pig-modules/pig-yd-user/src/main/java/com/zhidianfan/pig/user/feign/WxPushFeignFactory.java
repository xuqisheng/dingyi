package com.zhidianfan.pig.user.feign;

import com.zhidianfan.pig.common.dto.WxSinglePushDTO;
import com.zhidianfan.pig.user.dto.ErrorTip;
import com.zhidianfan.pig.user.dto.Tip;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/09
 * @Modified By:
 */

@Service
public class WxPushFeignFactory implements FallbackFactory<WxPushFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public WxPushFeign create(Throwable throwable) {
        log.info(throwable.getMessage());
        return new WxPushFeign() {
            @Override
            public Tip pushToSingle(WxSinglePushDTO pushDTO) {
                log.error("调用 pushToSingle 异常");
                return new ErrorTip();
            }

        };
    }
}
