package com.zhidianfan.pig.yd.moduler.resv.service.rmi.fallback;

import com.zhidianfan.pig.common.util.R;
import com.zhidianfan.pig.common.vo.SysRole;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysDeptFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.SysRoleFeign;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.RoleDTO;
import com.zhidianfan.pig.yd.moduler.resv.service.rmi.dto.SysDept;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author Bazinga
 * @Description
 * @Date Create in 2018/9/25
 * @Modified By:
 */
@Component
public class SysRoleFeignFactory implements FallbackFactory<SysRoleFeign> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public SysRoleFeign create(Throwable throwable) {
        log.error("SysRoleFeign调用异常：{}", throwable.getMessage());
        return new SysRoleFeign() {


            @Override
            public SysRole role(Integer id) {
                log.error("调用异常 {}.{}:{} ", "SysRoleFeign", "role", "GET");
                return null;
            }

            @Override
            public R<Boolean> role(RoleDTO roleDto) {
                log.error("调用异常 {}.{}:{} ", "SysRoleFeign", "role", "POST");
                return null;
            }

            @Override
            public R<Boolean> roleUpdate(RoleDTO roleDto) {
                log.error("调用异常 {}.{}:{} ", "SysRoleFeign", "roleUpdate", "PUT");
                return null;
            }

            @Override
            public R<Boolean> roleDel(Integer id) {
                log.error("调用异常 {}.{}:{} ", "SysRoleFeign", "roleDel", "DELETE");
                return null;
            }

            @Override
            public RoleDTO findRoleByRolecode(String roleCode) {
                log.error("调用异常 {}.{}:{} ", "SysRoleFeign", "findRoleByRolecode", "GET");
                return null;
            }
        };
    }
}
