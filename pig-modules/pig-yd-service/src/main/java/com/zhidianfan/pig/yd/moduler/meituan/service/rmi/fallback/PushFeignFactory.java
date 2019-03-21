package com.zhidianfan.pig.yd.moduler.meituan.service.rmi.fallback;

import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.PushFeign;
import com.zhidianfan.pig.yd.moduler.resv.dto.WSResultDTO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/10/17
 * @Modified By:
 */
@Component
public class PushFeignFactory implements FallbackFactory<PushFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public PushFeign create(Throwable throwable){
        log.error("PushFeign调用异常：{}", throwable.getMessage());

        return new PushFeign() {
            @Override
            public void pushMsg(String type,
                                String username,
                                String msgSeq,
                                String businessId,
                                String msg) {
                log.error("调用异常 {}.{} ", "PushFeign", "pushMsg");
            }

            @Override
            public WSResultDTO getOnlineSession(Integer hotelId) {
                log.error("调用异常 {}.{} ", "PushFeign", "getOnlineSession");

                return null;
            }
        };
    }
}
