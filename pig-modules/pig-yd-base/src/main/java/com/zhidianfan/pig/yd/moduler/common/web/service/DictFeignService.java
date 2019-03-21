package com.zhidianfan.pig.yd.moduler.common.web.service;

import com.zhidianfan.pig.yd.moduler.common.dto.SysDict;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@FeignClient(name = "pig-upms-service",fallbackFactory = DictFeignServiceFactory.class)
public interface DictFeignService {

    /**
     * 获取指定码表
     * @param type
     * @return
     */
    @GetMapping("/dict/type/{type}")
    List<SysDict> findDictByType(@PathVariable(name = "type") String type);
}
