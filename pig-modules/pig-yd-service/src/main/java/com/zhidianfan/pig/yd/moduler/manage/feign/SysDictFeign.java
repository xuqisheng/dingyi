package com.zhidianfan.pig.yd.moduler.manage.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
@FeignClient(name = "pig-upms-service",fallbackFactory = SysDictFeignFactory.class)
public interface SysDictFeign {

    @GetMapping("/dict/get")
    String getDict(@RequestParam(name = "value") String value,
                   @RequestParam(name = "type") String type);

}
