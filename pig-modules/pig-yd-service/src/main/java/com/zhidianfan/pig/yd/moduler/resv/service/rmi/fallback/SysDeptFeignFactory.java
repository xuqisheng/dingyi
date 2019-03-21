package com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback;

import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysDeptFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.SysDept;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/21
 * @Modified By:
 */
@Component
public class SysDeptFeignFactory implements FallbackFactory<SysDeptFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public SysDeptFeign create(Throwable throwable) {
        log.error("SysDeptFeign调用异常：{}", throwable.getMessage());
        return new SysDeptFeign() {

            @Override
            public SysDept get(Integer id) {
                log.error("调用异常 {}.{} ", "SysDeptFeign", "get");
                return null;
            }

            @Override
            public SysDept findDeptByDeptname(String deptname){
                log.error("调用异常 {}.{}", "SysDeptFeign", "findDeptByDeptname");
                return null;
            }

            @Override
            public Boolean add(SysDept sysDept) {
                log.error("调用异常 {}.{} ", "SysDeptFeign", "add");
                return Boolean.FALSE;
            }

            @Override
            public Boolean delete(Integer id) {
                log.error("调用异常 {}.{} ", "SysDeptFeign", "delete");
                return  Boolean.FALSE;
            }

            @Override
            public Boolean edit(SysDept sysDept) {
                log.error("调用异常 {}.{} ", "SysDeptFeign", "edit");
                return  Boolean.FALSE;
            }
        };
    }
}
