package com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback;

import com.zhidianfan.pig.yd.moduler.resv.bo.DeskBo;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysDeptFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysTableFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @User: ljh
 * @Date: 2018-11-07
 * @Time: 13:37
 */
@Slf4j
@Component
public class SysTableFeignFactory implements FallbackFactory<SysTableFeign> {


    @Override
    public SysTableFeign create(Throwable throwable) {
        log.error("SysTableFeign调用异常：{}", throwable.getMessage());
        return new SysTableFeign() {
            @Override
            public List<DeskBo> deskList(Integer businessId, String status) {
                log.error("/table/list ：{} {} ", businessId,status);
                return Arrays.asList();
            }
        };
    }
}
