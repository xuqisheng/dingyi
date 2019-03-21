package com.zhidianfan.pig.log.feign;

import com.zhidianfan.pig.log.dto.CommonRes;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/10/26
 * @Modified By:
 */
@FeignClient(name = "pig-zipkin-db",fallbackFactory = ZipLogFeignFactory.class)
public interface ZipLogFeign {

    @PostMapping("/ziplog/delete/all")
    CommonRes deleteAllZipkinData(@RequestParam("taskId") String taskId);
    @PostMapping("/syslog/delete/all")
    CommonRes deleteAllSysLogData(@RequestParam("taskId") String taskId);
}
