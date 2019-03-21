package com.zhidianfan.pig.user.feign;

import com.zhidianfan.pig.user.bo.OrderBo;
import com.zhidianfan.pig.user.dto.OrderDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *订单相关
 */
@FeignClient(name = "pig-yd-service",fallbackFactory = OrderFeignFactory.class)
public interface OrderFeign {

    @PostMapping("/resvorder/order/search")
    List<OrderBo> searchOrder(OrderDTO orderDTO);


    @GetMapping("/resvorder/order/batchNo")
    List<OrderBo> findByBatchNo(@RequestParam("batchNo") String batchNo);

}
