package com.zhidianfan.pig.ydmonitor.feign;

import com.zhidianfan.pig.common.entity.SysDict;
import com.zhidianfan.pig.ydmonitor.feign.fallback.DictFeignFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@FeignClient(name = "pig-upms-service",fallbackFactory = DictFeignFallbackFactory.class)
public interface DictFeign {
    @GetMapping("/type/{type}")
    List<SysDict> findDictByType(@PathVariable String type);
}
