package com.zhidianfan.pig.log.feign;

import com.zhidianfan.pig.common.constant.Tip;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
@FeignClient(name = "pig-yd-service", fallbackFactory = YdServiceLogFeignFactory.class)
public interface YdServiceLogFeign {

    @GetMapping("/log/alllog/delete")
    Tip deleteByConfig(@RequestParam(name = "tableName") String tableName
            , @RequestParam(name = "filedName") String filedName
            , @RequestParam(name = "count") Integer count);

}
