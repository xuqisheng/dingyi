package com.zhidianfan.pig.user.feign;

import com.zhidianfan.pig.user.bo.OrderBo;
import com.zhidianfan.pig.user.dto.OrderDTO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

@Service
public class OrderFeignFactory implements FallbackFactory<OrderFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public OrderFeign create(Throwable throwable) {
        log.info(throwable.getMessage());
        return new OrderFeign() {
            @Override
            public List<OrderBo> searchOrder(OrderDTO orderDTO) {
                log.error("调用 order/search 异常，type：{}", orderDTO);
                return null;
            }

            @Override
            public List<OrderBo> findByBatchNo(String batchNo) {
                log.error("调用 order/batchNo 异常，type：{}", batchNo);
                return null;
            }
        };
    }
}
