package com.zhidianfan.pig.yd.moduler.resv.service.rmi;

import com.zhidianfan.pig.yd.moduler.resv.bo.DeskBo;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback.SysTableFeignFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-07
 * @Time: 13:37
 */
@FeignClient(name = "pig-yd-user",fallbackFactory = SysTableFeignFactory.class)
public interface SysTableFeign {


    @GetMapping(value = "/table/list")
    List<DeskBo> deskList(@RequestParam("businessId") Integer businessId,@RequestParam("status") String status);

}
